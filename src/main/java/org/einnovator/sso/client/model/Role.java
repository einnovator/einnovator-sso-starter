package org.einnovator.sso.client.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.einnovator.util.model.EntityBase;
import org.einnovator.util.model.ToStringCreator;
import org.einnovator.util.security.Authority;
import org.einnovator.util.security.AuthorityBuilder;
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

	private Integer userCount;
	
	private Boolean builtin;

	private String app;

	private Boolean global;
	
	private List<Permission> permissions;
	
	private Group group;

	
	/**
	 * Create instance of {@code Role}.
	 *
	 */
	public Role() {
	}
	
	/**
	 * Create instance of {@code Role}.
	 *
	 * @param name
	 * @param builtin
	 */
	public Role(String name, Boolean builtin) {
		this.name = name;
		this.builtin = builtin;
	}
	
	/**
	 * Get the value of property {@code name}.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the value of property {@code name}.
	 *
	 * @param name the value of property name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the value of property {@code type}.
	 *
	 * @return the type
	 */
	public RoleType getType() {
		return type;
	}

	/**
	 * Set the value of property {@code type}.
	 *
	 * @param type the value of property type
	 */
	public void setType(RoleType type) {
		this.type = type;
	}

	/**
	 * Get the value of property {@code description}.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the value of property {@code description}.
	 *
	 * @param description the value of property description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the value of property {@code userCount}.
	 *
	 * @return the userCount
	 */
	public Integer getUserCount() {
		return userCount;
	}

	/**
	 * Set the value of property {@code userCount}.
	 *
	 * @param userCount the value of property userCount
	 */
	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	/**
	 * Get the value of property {@code builtin}.
	 *
	 * @return the builtin
	 */
	public Boolean getBuiltin() {
		return builtin;
	}

	/**
	 * Set the value of property {@code builtin}.
	 *
	 * @param builtin the value of property builtin
	 */
	public void setBuiltin(Boolean builtin) {
		this.builtin = builtin;
	}

	/**
	 * Get the value of property {@code app}.
	 *
	 * @return the app
	 */
	public String getApp() {
		return app;
	}

	/**
	 * Set the value of property {@code app}.
	 *
	 * @param app the value of property app
	 */
	public void setApp(String app) {
		this.app = app;
	}

	/**
	 * Get the value of property {@code global}.
	 *
	 * @return the global
	 */
	public Boolean getGlobal() {
		return global;
	}

	/**
	 * Set the value of property {@code global}.
	 *
	 * @param global the value of property global
	 */
	public void setGlobal(Boolean global) {
		this.global = global;
	}

	/**
	 * Get the value of property {@code permissions}.
	 *
	 * @return the permissions
	 */
	public List<Permission> getPermissions() {
		return permissions;
	}

	/**
	 * Set the value of property {@code permissions}.
	 *
	 * @param permissions the value of property permissions
	 */
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * Get the value of property {@code group}.
	 *
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * Set the value of property {@code group}.
	 *
	 * @param group the value of property group
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * Set the value of property {@code name}.
	 *
	 * @param name the value of property name
	 */
	public Role withName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Set the value of property {@code type}.
	 *
	 * @param type the value of property type
	 */
	public Role withType(RoleType type) {
		this.type = type;
		return this;
	}

	/**
	 * Set the value of property {@code description}.
	 *
	 * @param description the value of property description
	 */
	public Role withDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Set the value of property {@code userCount}.
	 *
	 * @param userCount the value of property userCount
	 */
	public Role withUserCount(Integer userCount) {
		this.userCount = userCount;
		return this;
	}

	/**
	 * Set the value of property {@code builtin}.
	 *
	 * @param builtin the value of property builtin
	 */
	public Role withBuiltin(Boolean builtin) {
		this.builtin = builtin;
		return this;
	}

	/**
	 * Set the value of property {@code app}.
	 *
	 * @param app the value of property app
	 */
	public Role withApp(String app) {
		this.app = app;
		return this;
	}

	/**
	 * Set the value of property {@code global}.
	 *
	 * @param global the value of property global
	 */
	public Role withGlobal(Boolean global) {
		this.global = global;
		return this;
	}

	/**
	 * Set the value of property {@code permissions}.
	 *
	 * @param permissions the value of property permissions
	 */
	public Role withPermissions(List<Permission> permissions) {
		this.permissions = permissions;
		return this;
	}

	/**
	 * Set the value of property {@code group}.
	 *
	 * @param group the value of property group
	 */
	public Role withGroup(Group group) {
		this.group = group;
		return this;
	}
	
	public Role withPermissions(Permission... permissions) {
		if (this.permissions==null) {
			this.permissions = new ArrayList<>();
		}
		this.permissions.addAll(Arrays.asList(permissions));
		return this;
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
		
	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
				.append("name", name)
				.append("builtin", builtin)
				.append("app", app)
				.append("global", global)
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
		String name = "ROLE_" + role.name;
		name = name.replace(' ', '_');
		if (role.group != null) {
			name += "@" + role.group.getUuid();
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
		return role != null ? role.permissions : null;
	}

	public static Role findRole(String roleId, List<Role> roles) {
		if (roles != null) {
			for (Role role : roles) {
				if (roleId.equals(role.getUuid()) || roleId.equals(role.name)
						|| roleId.equals(makeGrantedAuthorityName(role.name))) {
					return role;
				}
			}
		}
		return null;
	}

	public static Role findRole(String roleId, Role[] roles) {
		if (roles != null) {
			for (Role role : roles) {
				if (roleId.equals(role.getUuid()) || roleId.equals(role.name)
						|| roleId.equals(makeGrantedAuthorityName(role.name))) {
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

	public static List<Authority> toAuthorities(Role role, List<User> users) {
		if (role==null) {
			return null;
		}
		List<Authority> authorities = new ArrayList<>();
		for (User user: users) {
			authorities.add(new AuthorityBuilder()
					.username(user.getUsername())
					.userData(user)
					.group(role.group!=null ? role.group.getUuid() : null)
					.groupData(role.group)
					.roles(role.name)
					.roleData(role)
					.build());
		}
		return authorities;
	}
}
