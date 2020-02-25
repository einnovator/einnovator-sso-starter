package org.einnovator.sso.client.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.einnovator.util.model.EntityBase;
import org.einnovator.util.model.ToStringCreator;
import org.einnovator.util.security.RoleBinding;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role extends EntityBase {	

	public static final String ROLE_PREFIX = "ROLE_";

	private String name;

	private String displayName;
	
	private RoleType type;

	private String description;

	private Integer userCount;
	
	private Boolean builtin;

	private String app;

	private Boolean global;
	
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
	 * @param name the name
	 * @param builtin true if group role prototype
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
	 * Get the value of property {@code displayName}.
	 *
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Set the value of property {@code displayName}.
	 *
	 * @param displayName the value of property displayName
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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
	
	//
	// With
	//

	/**
	 * Set the value of property {@code name}.
	 *
	 * @param name the value of property name
	 * @return this {@code Role}
	 */
	public Role withName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Set the value of property {@code type}.
	 *
	 * @param type the value of property type
	 * @return this {@code Role}
	 */
	public Role withType(RoleType type) {
		this.type = type;
		return this;
	}

	/**
	 * Set the value of property {@code description}.
	 *
	 * @param description the value of property description
	 * @return this {@code Role}
	 */
	public Role withDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Set the value of property {@code userCount}.
	 *
	 * @param userCount the value of property userCount
	 * @return this {@code Role}
	 */
	public Role withUserCount(Integer userCount) {
		this.userCount = userCount;
		return this;
	}

	/**
	 * Set the value of property {@code builtin}.
	 *
	 * @param builtin the value of property builtin
	 * @return this {@code Role}
	 */
	public Role withBuiltin(Boolean builtin) {
		this.builtin = builtin;
		return this;
	}

	/**
	 * Set the value of property {@code app}.
	 *
	 * @param app the value of property app
	 * @return this {@code Role}
	 */
	public Role withApp(String app) {
		this.app = app;
		return this;
	}

	/**
	 * Set the value of property {@code global}.
	 *
	 * @param global the value of property global
	 * @return this {@code Role}
	 */
	public Role withGlobal(Boolean global) {
		this.global = global;
		return this;
	}


	/**
	 * Set the value of property {@code group}.
	 *
	 * @param group the value of property group
	 * @return this {@code Role}
	 */
	public Role withGroup(Group group) {
		this.group = group;
		return this;
	}
	
	//
	// util
	//
	
	@JsonIgnore
	public String getFullName() {
		if (group!=null && group.getUuid()!=null) {
			return makeRoleName(name, group.getUuid());
		}
		return name;
	}
	
	
	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
				.append("name", name)
				.append("displayName", displayName)
				.append("type", type)
				.append("builtin", builtin)
				.append("app", app)
				.append("global", global)
				.append("userCount", userCount)
				.append("group", group)
				.append("description", description);
	}
	
	//
	// static util
	//

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
			role = normalize(role);
			return role + "@" + groupId;
		}
		return role;
	}

	public static String normalize(String role) {
		if (role!=null) {
			role = role.trim();
			if (!role.startsWith("ROLE_")) {
				role = "ROLE_" + role;				 
			}
		}
		return role;
	}


	public static String makePermissionName(String permission, String groupId) {
		if (!StringUtils.isEmpty(groupId)) {
			return permission + "@" + groupId;
		}
		return permission;
	}

	public static List<RoleBinding> toRoleBindings(Role role, List<User> users) {
		if (role==null) {
			return null;
		}
		List<RoleBinding> bindings = new ArrayList<>();
		for (User user: users) {
			bindings.add(new RoleBinding()
					.withUsername(user.getUsername())
					.withUser(user)
					.withGroupId(role.group!=null ? role.group.getUuid() : null)
					.withGroup(role.group)
					.withRoleName(role.name)
					.withRole(role)
					);
		}
		return bindings;
	}
	
	public static boolean hasAnyRole(Collection<? extends GrantedAuthority> authorities, String... roles) {
		if (roles!=null) {
			for (String role: roles) {
				if (hasRole(role, authorities)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean hasRole(String role, Collection<? extends GrantedAuthority> authorities) {
		if (authorities != null) {
			for (GrantedAuthority authority : authorities) {
				if (equals(role, authority.getAuthority())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean hasAnyRoleInGroup(Collection<? extends GrantedAuthority> authorities, String groupId, String... roles) {
		for (String role : roles) {
			if (hasRole(makeRoleName(role, groupId), authorities)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasRoleInGroup(String role, String groupId, Collection<? extends GrantedAuthority> authorities) {
		role = makeRoleName(role, groupId);
		return hasRole(role, authorities);
	}
	
	public static boolean equals(String role0, String role1) {
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
	

}
