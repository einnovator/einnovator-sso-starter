package org.einnovator.sso.client.manager;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.config.SsoClientConfiguration;
import org.einnovator.sso.client.config.SsoClientContext;
import org.einnovator.sso.client.model.Role;
import org.einnovator.sso.client.model.RoleBinding;
import org.einnovator.sso.client.model.RoleType;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.RoleFilter;
import org.einnovator.sso.client.modelx.RoleOptions;
import org.einnovator.sso.client.modelx.UserFilter;
import org.einnovator.util.MappingUtils;
import org.einnovator.util.security.SecurityUtil;
import org.einnovator.util.web.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.client.HttpStatusCodeException;

public class RoleManagerImpl extends ManagerBase implements RoleManager {

	public static final String CACHE_ROLE = "Role";
	public static final String CACHE_GROUP_ROLES = "GroupRoles";


	public static final String ATTRIBUTE_ROLES = "ROLES";

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private UserManager userManager;

	@Autowired
	private SsoClient client;

	@Autowired
	private SsoClientConfiguration config;

	@Autowired
	private CacheManager cacheManager;

	public RoleManagerImpl() {
	}
	

	public RoleManagerImpl(UserManager userManager, SsoClient client) {
		this.userManager = userManager;
		this.client = client;
	}



	public RoleManagerImpl(SsoClient client, UserManager userManager, CacheManager cacheManager) {
		this.client = client;
		this.userManager = userManager;
		this.cacheManager = cacheManager;
	}

	public RoleManagerImpl(SsoClient client, UserManager userManager) {
		this.client = client;
		this.userManager = userManager;
	}

	public RoleManagerImpl(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public boolean hasAnyRole(Principal principal, String... roles) {
		Collection<? extends GrantedAuthority> authorities = SecurityUtil.getAuthorities();
		return Role.hasAnyRole(authorities, roles);
	}

	@Override
	public boolean hasAnyRole(String... roles) {
		return hasAnyRole(SecurityUtil.getPrincipal(), roles);
	}

	@Override
	public boolean hasAnyRoleInGroup(Principal principal, String groupId, String... roles) {
		Collection<? extends GrantedAuthority> authorities = SecurityUtil.getAuthorities();
		return Role.hasAnyRoleInGroup(groupId, authorities, roles);
	}

	@Override
	public boolean hasAnyRoleInGroup(String groupId, String... roles) {
		return hasAnyRoleInGroup(SecurityUtil.getPrincipal(), groupId, roles);
	}

	@Override
	public Page<Role> listRolesForUser(String userId, RoleFilter filter, Pageable options, SsoClientContext context) {
		try {
			return client.listRolesForUser(userId, filter, options, context);
		} catch (RuntimeException e) {
			logger.error("listRolesForUser: " + userId + " " + options + " " + e);
			return null;
		}
	}

	@Override
	public Page<Role> listRolesForUserInGroup(String userId, String groupId, RoleFilter filter, Pageable options, SsoClientContext context) {
		try {
			return client.listRolesForUserInGroup(userId, groupId, filter, options, context);
		} catch (RuntimeException e) {
			logger.error("listRolesForUserInGroup: " + userId + " " + groupId + " " + options + " " + e);
			return null;
		}
	}


	@Override
	public boolean isAdmin(Principal principal) {
		if (principal == null) {
			return false;
		}
		if (config.getAccess().isAdmin(principal.getName())) {
			return true;
		}
		String[] admin = getAdmin();
		if (hasAnyRole(admin)) {
			return true;
		}
		return false;
	}

	protected String[] getAdmin() {
		return new String[] { Role.ROLE_ADMIN };
	}

	protected String[] getSuperuser() {
		return new String[] { Role.ROLE_SUPERUSER };
	}

	@Override
	public boolean isClient(Principal principal) {
		if (principal == null) {
			return false;
		}
		if (hasAnyRole(new String[] { Role.ROLE_CLIENT })) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasAnyRoleUser(String userId, SsoClientContext context, String... roles) {
		User user = userManager.getUser(userId, context);
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
	public boolean hasAnyRoleUserInGroup(String userId, String groupId, SsoClientContext context, String... roles) {
		User user = userManager.getUser(userId, context);
		if (user == null) {
			return false;
		}
		List<Role> roles2 = user.getRoles();
		if (roles2 == null) {
			return false;
		}
		for (String role : roles) {
			role = Role.makeRoleName(role, groupId);
			for (Role role2 : roles2) {
				if (role.equalsIgnoreCase(role2.getFullName())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isAdmin(String userId, SsoClientContext context) {
		String[] admin = getAdmin();
		if (hasAnyRoleUser(userId, context, admin)) {
			return true;
		}
		return false;
	}


	@Override
	public URI createRole(Role role, RequestOptions options, SsoClientContext context) {
		try {
			URI uri = client.createRole(role, options, context);
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
	public Role getRole(String roleId, RoleOptions options, SsoClientContext context) {
		try {
			Role group = client.getRole(roleId, options, context);
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
	public Role updateRole(Role role, RequestOptions options, SsoClientContext context) {
		try {
			client.updateRole(role, options, context);
			if (role == null) {
				logger.error("updateRole: " + role);
			}
			return role;
		} catch (RuntimeException e) {
			logger.error("updateRole: " + role + "  " + e);
			return null;
		}
	}

	@Override
	public Page<Role> listRoles(RoleFilter filter, Pageable options, SsoClientContext context) {
		try {
			Page<Role> groups = client.listRoles(filter, options, context);
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
	public boolean deleteRole(String roleId, RequestOptions options, SsoClientContext context) {
		try {
			client.deleteRole(roleId, options, context);
			if (roleId == null) {
				logger.error("deleteRole: " + roleId);
			}
			return true;
		} catch (RuntimeException e) {
			logger.error("deleteRole: " + roleId);
			return false;
		}
	}

	@Override
	public Page<RoleBinding> listRoleBindings(String roleId, UserFilter filter, Pageable pageable, SsoClientContext context) {
		try {
			Page<RoleBinding> bindings = client.listRoleBindings(roleId, filter, pageable, context);
			if (bindings == null) {
				logger.error("listRoleBindings: " + roleId);
			}
			return bindings;
		} catch (RuntimeException e) {
			logger.error("listRoleBindings: " + roleId + "  " + e);
			return null;
		}
	}

	@Override
	public Integer countRoleBindings(String roleId, UserFilter filter, SsoClientContext context) {
		try {
			Integer n = client.countRoleBindings(roleId, filter, context);
			return n;
		} catch (RuntimeException e) {
			logger.error("countRoleBindings: " + roleId + "  " + e);
			return null;
		}
	}

	@Override
	public boolean assignRole(String userId, String roleId, RequestOptions options, SsoClientContext context) {
		try {
			if (roleId == null) {
				logger.error("assignRole: " + userId + " " + roleId);
			}
			client.assignRole(userId, roleId, options, context);
			return true;
		} catch (RuntimeException e) {
			logger.error("assignRole: " + userId + " " + roleId + " " + e);
			return false;
		}
	}

	@Override
	public boolean unassignRole(String userId, String roleId, RequestOptions options, SsoClientContext context) {
		try {
			client.unassignRole(userId, roleId, options, context);
			if (roleId == null) {
				logger.error("removeFromRole: " + userId + " " + roleId);
			}
			return true;
		} catch (RuntimeException e) {
			logger.error("removeFromRole: " + userId + " " + roleId + " " + e);
			return false;
		}
	}


	@Override
	public List<Role> listRolesForGroup(String groupId, SsoClientContext context) {
		String key = groupId != null ? groupId : "";
		Role[] roles = getCacheValue(Role[].class, getGroupRolesCache(), key);
		if (roles != null) {
			return new ArrayList<>(Arrays.asList(roles));
		}
		try {
			RoleFilter filter = new RoleFilter();
			if (groupId == null) {
				filter.setType(RoleType.GLOBAL);
			}
			filter.setGroup(groupId);
			Page<Role> page = listRoles(filter, new PageRequest(0, Integer.MAX_VALUE), context);
			if (page == null || page.getContent() == null) {
				return null;
			}
			putCacheValue(page.getContent().toArray(new Role[page.getContent().size()]), getGroupRolesCache(), key);
			return page.getContent();
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
				logger.error(String.format("listRolesForGroup: %s %s", groupId, e));
			}
			return null;
		} catch (RuntimeException e) {
			logger.error(String.format("listRolesForGroup: %s %s", groupId, e));
			return null;
		}
	}

	public Cache getRoleCache() {
		Cache cache = cacheManager.getCache(CACHE_ROLE);
		return cache;
	}

	public Cache getGroupRolesCache() {
		Cache cache = cacheManager.getCache(CACHE_GROUP_ROLES);
		return cache;
	}


	@EventListener
	public void onEvent(ApplicationEvent event) {
		if (!this.getClass().equals(RoleManagerImpl.class)) {
			return;
		}
		if (event instanceof PayloadApplicationEvent) {
			Role role = getNotificationSource(((PayloadApplicationEvent<?>) event).getPayload(), Role.class);
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("onEvent: %s %s", role, ((PayloadApplicationEvent<?>) event).getPayload()));
			}
			if (role != null) {
				updateCaches(role);
			}
			return;
		}

	}
	private void updateCaches(Role role) {
		Cache cache = getRoleCache();
		if (cache != null && role.getUuid() != null) {
			Role role2 = getCacheValue(Role.class, cache, role.getUuid());
			if (role2 != null) {
				MappingUtils.updateObjectFrom(role2, role);
			} else {
				//putCacheValue(role.getUuid(), cache, role);
			}
		}
		Cache cache2 = getGroupRolesCache();
		if (cache2 != null) {

			String key = role.getGroup() != null ? role.getGroup().getUuid(): (role.getType()==null || role.getType()==RoleType.GROUP) ? "" : null;
			if (key != null) {
				Role[] roles = getCacheValue(Role[].class, getGroupRolesCache(), key);
				if (roles != null) {
					Role role2 = Role.findRole(role.getUuid(), roles);
					if (role2 != null) {
						MappingUtils.updateObjectFrom(role2, role);
					}
				}
			}

		}

	}


}
