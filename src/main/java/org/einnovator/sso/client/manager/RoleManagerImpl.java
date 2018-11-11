package org.einnovator.sso.client.manager;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.model.Permission;
import org.einnovator.sso.client.model.Role;
import org.einnovator.sso.client.model.RoleFilter;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;

public class RoleManagerImpl implements RoleManager {

	public static final String ROLE_PREFIX = "ROLE_";

	public static final String ATTRIBUTE_ROLES = "ROLES";

	public static final String ROLE_CLIENT = "ROLE_CLIENT";

	public static final String ROLE_USER = "ROLE_USER";

	private static final String ROLE_ADMIN = "admin";

	private static final String ROLE_SUPERUSER = "superuser";

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private UserManager userManager;

	@Autowired
	private GroupManager groupManager;

	@Autowired
	private SsoClient client;

	public RoleManagerImpl() {
	}

	@Override
	public boolean hasAnyRole(Principal principal, String... roles) {
		for (String role : roles) {
			if (hasAnyRoleInternal(principal, role)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasAnyRoleInternal(Principal principal, String role) {
		Collection<? extends GrantedAuthority> authorities = SsoClient.getAuthorities(principal);
		if (authorities == null) {
			authorities = SsoClient.getAuthorities();
		}
		if (authorities != null) {
			for (GrantedAuthority authority : authorities) {
				if (roleEquals(role, authority.getAuthority())) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean roleEquals(String role0, String role1) {
		if (role0 == null || role1 == null) {
			return false;
		}
		if (role0.equalsIgnoreCase(role1)) {
			return true;
		}
		if (role0.startsWith(ROLE_PREFIX)) {
			if (!role1.startsWith(ROLE_PREFIX)) {
				if (role0.substring(ROLE_PREFIX.length()).equalsIgnoreCase(role1)) {
					return true;
				}
			}
		} else {
			if (role1.startsWith(ROLE_PREFIX)) {
				if (role1.substring(ROLE_PREFIX.length()).equalsIgnoreCase(role0)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean hasAnyRole(String... roles) {
		return hasAnyRole(SsoClient.getPrincipal(), roles);
	}

	@Override
	public boolean hasAnyRoleInGroup(Principal principal, String groupId, String... roles) {
		for (String role : roles) {
			if (hasAnyRole(principal, makeRoleName(role, groupId))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasAnyRoleInGroup(String groupId, String... roles) {
		return hasAnyRoleInGroup(SsoClient.getPrincipal(), groupId, roles);
	}

	//
	@Override
	public boolean hasAnyPermission(Principal principal, String... perms) {
		for (String perm : perms) {
			if (hasAnyPermissionInternal(principal, perm)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasAnyPermissionInGroup(Principal principal, String groupId, String... perms) {
		for (String perm : perms) {
			if (hasAnyPermissionInternal(principal, makePermissionName(perm, groupId))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasAnyPermission(String... perms) {
		return hasAnyPermission(SsoClient.getPrincipal(), perms);
	}

	@Override
	public boolean hasAnyPermissionInGroup(String groupId, String... perms) {
		return hasAnyPermissionInGroup(SsoClient.getPrincipal(), groupId, perms);
	}

	private boolean hasAnyPermissionInternal(Principal principal, String perm) {
		Collection<? extends GrantedAuthority> authorities = principal != null ? SsoClient.getAuthorities(principal)
				: SsoClient.getAuthorities();
		if (authorities == null) {
			authorities = SsoClient.getAuthorities();
		}
		if (authorities != null) {
			for (GrantedAuthority authority : authorities) {
				if (perm.equals(authority.getAuthority())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean hasAnyPermissionUserInGroup(String userId, String groupId, String... perms) {
		List<Role> roles = listRolesForUserInGroup(userId, groupId);
		if (roles != null) {
			for (Role role : roles) {
				for (String perm : perms) {
					if (role.findPermission(perm) != null) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public List<Permission> getPermissionsUserInGroup(String userId, String groupId) {
		List<Role> roles = listRolesForUserInGroup(userId, groupId);
		List<Permission> perms = new ArrayList<>();
		if (roles != null) {
			for (Role role : roles) {
				if (role.getPermissions() != null) {
					for (Permission perm : role.getPermissions()) {
						if (Permission.find(perm.getKey(), perms) == null) {
							perms.add(perm);
						}
					}
				}
			}
		}
		return perms;
	}

	@Override
	public Map<String, Boolean> getPermissionMapUserInGroup(String userId, String groupId) {
		List<Permission> perms = getPermissionsUserInGroup(userId, groupId);
		if (perms == null) {
			return null;
		}
		Map<String, Boolean> map = new LinkedHashMap<>();
		for (Permission perm : perms) {
			map.put(perm.getKey(), true);
		}
		return map;
	}

	@Override
	public boolean hasAnyPermissionUser(String userId, String... perms) {
		List<Role> roles = listRolesForUser(userId);
		if (roles != null) {
			for (Role role : roles) {
				for (String perm : perms) {
					if (role.findPermission(perm) != null) {
						return true;
					}
				}
			}
		}
		return false;

	}

	@Override
	public Page<Role> listRolesForUser(String userId, Pageable options) {
		try {
			return client.listRolesForUser(userId, options);
		} catch (RuntimeException e) {
			logger.error("listRolesForUser: " + userId + " " + options + " " + e);
			return null;
		}
	}

	@Override
	public Page<Role> listRolesForUserInGroup(String userId, String groupId, Pageable options) {
		try {
			return client.listRolesForUserInGroup(userId, groupId, options);
		} catch (RuntimeException e) {
			logger.error("listRolesForUserInGroup: " + userId + " " + groupId + " " + options + " " + e);
			return null;
		}
	}

	@Override
	public List<Role> listRolesForUser(String userId) {
		try {
			return client.listRolesForUser(userId);
		} catch (RuntimeException e) {
			logger.error("listRolesForUser: " + e + " " + userId + " " + e);
			return null;
		}
	}

	@Override
	public List<Role> listRolesForUserInGroup(String userId, String groupId) {
		try {
			return client.listRolesForUserInGroup(userId, groupId);
		} catch (RuntimeException e) {
			logger.error("listRolesForUserInGroup: " + e + " " + userId);
			return null;
		}

	}

	@Override
	public String makeRoleName(String role, String groupId) {
		if (!StringUtils.isEmpty(groupId)) {
			return role + "@" + groupId;
		}
		return role;
	}

	public String makeRoleName(Role role) {
		return makeRoleName(role.getName(), role.getGroup()!=null ? role.getGroup().getUuid() : null);
	}

	@Override
	public String makePermissionName(String permission, String groupId) {
		if (!StringUtils.isEmpty(groupId)) {
			return permission + "@" + groupId;
		}
		return permission;
	}

	@Override
	public boolean isAdmin(Principal principal) {
		if (principal == null) {
			return false;
		}
		String[] admin = getAdmin();
		if (hasAnyRole(admin)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isSuperuser(Principal principal) {
		if (principal == null) {
			return false;
		}
		String[] superuser = getSuperuser();
		if (hasAnyRole(superuser)) {
			return true;
		}
		return false;
	}



	@Override
	public boolean isMember(String userId, String... groups) {
		if (groups != null) {
			for (String groupId : groups) {
				if (groupManager.isMember(userId, groupId)) {
					return true;
				}
			}
		}
		return false;
	}


	protected String[] getAdmin() {
		return new String[] { ROLE_ADMIN };
	}

	protected String[] getSuperuser() {
		return new String[] { ROLE_SUPERUSER };
	}

	@Override
	public boolean isClient(Principal principal) {
		if (principal == null) {
			return false;
		}
		if (hasAnyRole(new String[] { ROLE_CLIENT })) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasAnyRoleUser(String userId, String... roles) {
		User user = userManager.getUser(userId);
		if (user == null) {
			return false;
		}
		List<Role> roles2 = user.getRoles();
		if (roles2 == null) {
			return false;
		}
		for (String role : roles) {
			for (Role role2 : roles2) {
				if (role2.getGroup()==null && role.equalsIgnoreCase(role2.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean hasAnyRoleUserInGroup(String userId, String groupId, String... roles) {
		User user = userManager.getUser(userId);
		if (user == null) {
			return false;
		}
		List<Role> roles2 = user.getRoles();
		if (roles2 == null) {
			return false;
		}
		for (String role : roles) {
			role = makeRoleName(role, groupId);
			for (Role role2 : roles2) {
				if (role.equalsIgnoreCase(makeRoleName(role2))) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isAdmin(String userId) {
		String[] admin = getAdmin();
		if (hasAnyRoleUser(userId, admin)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isSuperuser(String userId) {
		String[] superuser = getSuperuser();
		if (hasAnyRoleUser(userId, superuser)) {
			return true;
		}
		return false;
	}

	@Override
	public URI createRole(Role role) {
		try {
			URI uri = client.createRole(role);
			if (uri == null) {
				logger.error("createRole: " + role);
			}
			return uri;
		} catch (RuntimeException e) {
			logger.error("createRole: " + role + " " + e);
			return null;
		}
	}

	@Override
	public Role getRole(String roleId) {
		try {
			Role group = client.getRole(roleId);
			if (group == null) {
				logger.error("getRole: " + group);
			}
			return group;
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
				logger.error("getRole:" + roleId + "  " + e);
			}
			return null;
		} catch (RuntimeException e) {
			logger.error("getRole: " + roleId + "  " + e);
			return null;
		}
	}

	@Override
	public void updateRole(Role role) {
		try {
			client.updateRole(role);
			if (role == null) {
				logger.error("updateRole: " + role);
			}
		} catch (RuntimeException e) {
			logger.error("updateRole: " + role + "  " + e);
			return;
		}
	}

	@Override
	public Page<Role> listRoles(RoleFilter filter, Pageable options) {
		try {
			Page<Role> groups = client.listRoles(filter, options);
			if (groups == null) {
				logger.error("listRoles: " + options);
			}
			return groups;
		} catch (RuntimeException e) {
			logger.error("getRole: " + options + "  " + e);
			return null;
		}
	}

	@Override
	public void deleteRole(String roleId) {
		try {
			client.deleteRole(roleId);
			if (roleId == null) {
				logger.error("deleteRole: " + roleId);
			}
		} catch (RuntimeException e) {
			logger.error("deleteRole: " + roleId);
		}
	}

	@Override
	public Page<User> listRoleMembers(String roleId, Pageable options, UserFilter filter) {
		try {
			Page<User> members = client.listRoleMembers(roleId, options, filter);
			if (members == null) {
				logger.error("listRoleMembers: " + roleId);
			}
			return members;
		} catch (RuntimeException e) {
			logger.error("listRoleMembers: " + roleId + "  " + e);
			return null;
		}
	}

	@Override
	public Integer countRoleMembers(String roleId, UserFilter filter) {
		try {
			Integer n = client.countRoleMembers(roleId, filter);
			return n;
		} catch (RuntimeException e) {
			logger.error("countRoleMembers: " + roleId + "  " + e);
			return null;
		}
	}

	@Override
	public void assignRole(String userId, String roleId) {
		try {
			if (roleId == null) {
				logger.error("assignRole: " + userId + " " + roleId);
			}
			client.assignRole(userId, roleId);
		} catch (RuntimeException e) {
			logger.error("assignRole: " + userId + " " + roleId + " " + e);
		}
	}

	@Override
	public void removeFromRole(String userId, String roleId) {
		try {
			client.removeFromRole(userId, roleId);
			if (roleId == null) {
				logger.error("removeFromRole: " + userId + " " + roleId);
			}
		} catch (RuntimeException e) {
			logger.error("removeFromRole: " + userId + " " + roleId + " " + e);
		}
	}

	@Override
	public boolean hasAnyPermissionUserInAnyGroup(String username, List<String> groupIds, String... perms) {
		for (String groupId: groupIds) {
			if (hasAnyPermissionUserInGroup(username, groupId, perms)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasAnyPermissionInAnyGroup(Principal principal, List<String> groupIds, String... perms) {
		for (String groupId: groupIds) {
			if (hasAnyPermissionInGroup(principal, groupId, perms)) {
				return true;
			}
		}
		return false;

	}

	@Override
	public boolean hasAnyPermissionInAnyGroup(List<String> groupId, String... perms) {
		return hasAnyPermissionInAnyGroup((Principal)null, groupId, perms);
	}


}
