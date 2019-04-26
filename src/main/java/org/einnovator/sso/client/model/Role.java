package org.einnovator.sso.client.model;

import java.util.List;

import org.einnovator.util.model.EntityBase;
import org.einnovator.util.model.ToStringCreator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role extends EntityBase {	

	private String name;
	
	private RoleType type;

	private String description;

	private String owner;
		
	private Integer userCount;
	
	private Boolean builtIn;
	
	private Boolean global;
	
	private List<Permission> permissions;
	
	private Group group;

	
	public Role() {
	}
	
	public Role(String name, Boolean builtIn) {
		this.name = name;
		this.builtIn = builtIn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	public RoleType getType() {
		return type;
	}

	public void setType(RoleType type) {
		this.type = type;
	}

	public Boolean getBuiltIn() {
		return builtIn;
	}

	public void setBuiltIn(Boolean builtIn) {
		this.builtIn = builtIn;
	}
	
	public Boolean getGlobal() {
		return global;
	}

	public void setGlobal(Boolean global) {
		this.global = global;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
	public Permission findPermission(String id) {
		if (permissions!=null) {
			for (Permission perm: permissions) {
				if (id.equals(perm.getUuid()) || id.equals((perm.getKey()))) {
					return perm;
				}
			}
		}
		return null;
	}
		
	/* (non-Javadoc)
	 * @see org.einnovator.util.model.ObjectBase#toString1(org.einnovator.util.model.ToStringCreator)
	 */
	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
				.append("name", name)
				.append("builtIn", builtIn)
				.append("global", global)
				.append("owner", owner)
				.append("userCount", userCount)
				.append("group", group)
				.append("description", description)
				.append("permissions", permissions);

	}

	public static boolean isRole(GrantedAuthority authority) {
		return authority.getAuthority().startsWith("ROLE_");
	}

	public static boolean isPermission(GrantedAuthority authority) {
		return !isRole(authority);
	}

	public static String makeGrantedAuthorityName(String name) {
		name = name.replace(' ', '_');
		return name;
	}

	public static GrantedAuthority makeGrantedAuthority(Role role) {
		String name = "ROLE_" + role.getName();
		name = name.replace(' ', '_');
		if (role.getGroup() != null) {
			name += "@" + role.getGroup().getUuid();
		}
		return new SimpleGrantedAuthority(name);
	}

	public static GrantedAuthority makeGrantedAuthority(Permission perm, Group group) {
		String name = perm.getKey();
		name = name.replace(' ', '_');
		if (group != null) {
			name += "@" + group.getUuid();
		}
		return new SimpleGrantedAuthority(name);
	}

	public static String getRoleName(GrantedAuthority authority) {
		if (isRole(authority)) {
			String name = authority.getAuthority().substring("ROLE_".length());
			int i = name.indexOf("@");
			if (i > 0 && i < name.length() - 1) {
				name = name.substring(0, i);
			}
			return name;
		}
		return null;
	}

	public static String getGroup(GrantedAuthority authority) {
		int i = authority.getAuthority().indexOf("@");
		return i > 0 && i < authority.getAuthority().length() - 1 ? authority.getAuthority().substring(i + 1) : null;
	}

	public static List<Permission> getPermissionForRole(String roleId, List<Role> roles) {
		Role role = findRole(roleId, roles);
		return role != null ? role.getPermissions() : null;
	}

	public static Role findRole(String roleId, List<Role> roles) {
		if (roles != null) {
			for (Role role : roles) {
				if (roleId.equals(role.getUuid()) || roleId.equals(role.getName())
						|| roleId.equals(makeGrantedAuthorityName(role.getName()))) {
					return role;
				}
			}
		}
		return null;
	}

	public static Role findRole(String roleId, Role[] roles) {
		if (roles != null) {
			for (Role role : roles) {
				if (roleId.equals(role.getUuid()) || roleId.equals(role.getName())
						|| roleId.equals(makeGrantedAuthorityName(role.getName()))) {
					return role;
				}
			}
		}
		return null;
	}

	public static String makeRoleName(String role, String groupId) {
		if (!StringUtils.isEmpty(groupId)) {
			if (!role.startsWith("ROLE_")) {
				role = "ROLE_" + role;
			}
			return role + "@" + groupId;
		}
		return role;
	}

	public static String makePermissionName(String permission, String groupId) {
		if (!StringUtils.isEmpty(groupId)) {
			return permission + "@" + groupId;
		}
		return permission;
	}

}
