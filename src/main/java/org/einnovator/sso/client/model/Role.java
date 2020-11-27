package org.einnovator.sso.client.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.einnovator.util.model.EntityBase;
import org.einnovator.util.model.ToStringCreator;
import org.einnovator.util.security.RoleBinding;
import org.einnovator.util.security.SecurityUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A {@code Role}.
 *
 * @author support@einnovator.org
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role extends EntityBase {	

	public static final String ROLE_PREFIX = "ROLE_";

	public static final String ROLE_CLIENT = "CLIENT";
	public static final String ROLE_USER = "USER";
	public static final String ROLE_GUEST = "GUEST";
	public static final String ROLE_ADMIN = "ADMIN";
	public static final String ROLE_SUPERUSER = "SUPERUSER";

	private String name;

	private String displayName;
	
	private RoleType type;

	private Group group;

	private String description;

	private Integer userCount;

	private Integer requestCount;

	private String app;

	
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
	 * @param displayName the displayName
	 * @param type the type
	 */
	public Role(String name, String displayName, RoleType type) {
		this.name = name;
		this.displayName = displayName;
		this.type = type;
	}
	
	/**
	 * Create instance of {@code Role}.
	 *
	 * @param name the name
	 * @param type the type
	 */
	public Role(String name, RoleType type) {
		this.name = name;
		this.type = type;
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
	 * Get the value of property {@code requestCount}.
	 *
	 * @return the requestCount
	 */
	public Integer getRequestCount() {
		return requestCount;
	}

	/**
	 * Set the value of property {@code requestCount}.
	 *
	 * @param requestCount the value of property requestCount
	 */
	public void setRequestCount(Integer requestCount) {
		this.requestCount = requestCount;
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
	 * Set the value of property {@code requestCount}.
	 *
	 * @param requestCount the value of property requestCount
	 * @return this {@code Role}
	 */
	public Role withRequestCount(Integer requestCount) {
		this.requestCount = requestCount;
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
				.append("app", app)
				.append("userCount", userCount)
				.append("requestCount", requestCount)
				.append("group", group!=null ? group.getUuid() + " " + group.getName() : null)
				.append("description", description);
	}
	
	//
	// static util
	//

	public static boolean isRole(GrantedAuthority authority) {
		return authority.getAuthority().startsWith(Role.ROLE_PREFIX);
	}


	public static GrantedAuthority makeGrantedAuthority(Role role) {
		return makeGrantedAuthority(role.getName(), role.getGroup());
	}

	public static GrantedAuthority makeGrantedAuthority(String name, Group group) {
		return makeGrantedAuthority(name, group!=null ? group.getUuid() : null);
	}

	public static GrantedAuthority makeGrantedAuthority(String name, String group) {
		if (name==null) {
			return null;
		}
		if (group != null && !group.isEmpty()) {
			name += "@" + group;
		}
		name = normalize(name);
		return new SimpleGrantedAuthority(name);
	}


	public static String getRoleName(GrantedAuthority authority) {
		if (isRole(authority)) {
			String name = authority.getAuthority();
			name = unnormalize(name);
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
				if (equals(roleId, role.getName()))
				if (roleId.equals(role.getUuid()) || equals(roleId, role.getName())) {
					return role;
				}
			}
		}
		return null;
	}

	public static Role findRole(String roleId, Role[] roles) {
		if (roles != null) {
			for (Role role : roles) {
				if (roleId.equals(role.getUuid()) || equals(roleId, role.getName())) {
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
			if (!role.startsWith(Role.ROLE_PREFIX)) {
				role = ROLE_PREFIX + role;				 
			}
		}
		return role;
	}

	public static String unnormalize(String role) {
		if (role!=null) {
			role = role.trim();
			if (role.startsWith(Role.ROLE_PREFIX)) {
				role = role.substring(Role.ROLE_PREFIX.length());
			}
			role = role.replace('_', ' ').replaceAll("\\s+","");
		}
		return role;
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
	
	public static boolean hasAnyRoleInGroup(String groupId, Collection<? extends GrantedAuthority> authorities, String... roles) {
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
		role0 = role0.trim();
		role1 = role1.trim();
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
	
	public static List<String> getGroups(Collection<? extends GrantedAuthority> authorities) {
		Set<String> ids = new HashSet<>();
		if (authorities==null) {
			return null;
		}
		for (GrantedAuthority authority: authorities) {
			String groupId = Role.getGroup(authority);
			if (groupId!=null) {
				ids.add(groupId);
			}
		}
		return new ArrayList<>(ids);
	}
	
	public static List<String> getPrincipalGroups() {
		return getGroups(SecurityUtil.getAuthorities());
	}


	public static boolean isPrincipalMember(String groupId) {
		List<String> gids = getPrincipalGroups();
		return gids!=null && gids.contains(groupId);
	}
}
