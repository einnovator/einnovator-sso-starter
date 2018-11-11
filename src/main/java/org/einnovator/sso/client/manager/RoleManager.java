package org.einnovator.sso.client.manager;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.einnovator.sso.client.model.Permission;
import org.einnovator.sso.client.model.Role;
import org.einnovator.sso.client.model.RoleFilter;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleManager {


	URI createRole(Role role);

	Role getRole(String roleId);

	void updateRole(Role role);

	Page<Role> listRoles(RoleFilter filter, Pageable pageable);

	void deleteRole(String roleId);

	Page<User> listRoleMembers(String roleId, Pageable pageable, UserFilter filter);

	Integer countRoleMembers(String roleId, UserFilter filter);

	void assignRole(String username, String roleId);

	void removeFromRole(String username, String roleId);

	Page<Role> listRolesForUser(String userId, Pageable pageable);

	Page<Role> listRolesForUserInGroup(String userId, String groupId, Pageable pageable);

	List<Role> listRolesForUser(String userId);

	List<Role> listRolesForUserInGroup(String userId, String groupId);

	boolean hasAnyRoleUser(String username, String... role);

	boolean hasAnyRoleUserInGroup(String username, String groupId, String... roles);

	boolean hasAnyRole(Principal principal, String... roles);

	boolean hasAnyRole(String... roles);

	boolean hasAnyRoleInGroup(Principal principal, String groupId, String... roles);

	boolean hasAnyRoleInGroup(String groupId, String... roles);

	boolean hasAnyPermissionUser(String username, String... perms);

	boolean hasAnyPermissionUserInGroup(String username, String groupId, String... perms);
	boolean hasAnyPermissionUserInAnyGroup(String username, List<String> groupId, String... perms);

	boolean hasAnyPermission(Principal principal, String... perms);

	boolean hasAnyPermission(String... perms);

	boolean hasAnyPermissionInGroup(Principal principal, String groupId, String... perms);
	boolean hasAnyPermissionInAnyGroup(Principal principal, List<String> groupId, String... perms);

	boolean hasAnyPermissionInGroup(String groupId, String... perms);
	boolean hasAnyPermissionInAnyGroup(List<String> groupId, String... perms);

	List<Permission> getPermissionsUserInGroup(String userId, String groupId);

	Map<String, Boolean> getPermissionMapUserInGroup(String userId, String groupId);

	String makeRoleName(String role, String groupId);

	String makePermissionName(String perm, String groupId);

	boolean isMember(String username, String... groups);

	// Generic utility roles
	boolean isAdmin(Principal principal);

	boolean isSuperuser(Principal principal);

	// User specified

	// Generic utility roles
	boolean isAdmin(String username);

	boolean isSuperuser(String username);


	boolean isClient(Principal principal);
	
}
