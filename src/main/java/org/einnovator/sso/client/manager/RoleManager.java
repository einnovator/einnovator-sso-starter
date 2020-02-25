package org.einnovator.sso.client.manager;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import org.einnovator.sso.client.config.SsoClientContext;
import org.einnovator.sso.client.model.Role;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.RoleFilter;
import org.einnovator.sso.client.modelx.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleManager {


	URI createRole(Role role, SsoClientContext context);

	Role getRole(String roleId, SsoClientContext context);

	void updateRole(Role role, SsoClientContext context);

	Page<Role> listRoles(RoleFilter filter, Pageable pageable, SsoClientContext context);

	void deleteRole(String roleId, SsoClientContext context);

	Page<User> listRoleMembers(String roleId, Pageable pageable, UserFilter filter, SsoClientContext context);

	Integer countRoleMembers(String roleId, UserFilter filter, SsoClientContext context);

	void assignRole(String username, String roleId, SsoClientContext context);

	void unassignRole(String username, String roleId, SsoClientContext context);

	Page<Role> listRolesForUser(String userId, Pageable pageable, SsoClientContext context);

	Page<Role> listRolesForUserInGroup(String userId, String groupId, Pageable pageable, SsoClientContext context);

	List<Role> listRolesForUser(String userId, SsoClientContext context);

	List<Role> listRolesForUserInGroup(String userId, String groupId, SsoClientContext context);

	boolean hasAnyRoleUser(String username, SsoClientContext context, String... role);

	boolean hasAnyRoleUserInGroup(String username, String groupId, SsoClientContext context, String... roles);

	boolean hasAnyRole(Principal principal, String... roles);

	boolean hasAnyRole(String... roles);

	boolean hasAnyRoleInGroup(Principal principal, String groupId, String... roles);

	boolean hasAnyRoleInGroup(String groupId, String... roles);

	boolean isMember(String username, SsoClientContext context, String... groups);

	
	List<Role> listGlobalRoles(SsoClientContext context);

	List<Role> listRolesForGroup(String groupId, SsoClientContext context);

	// Generic utility roles
	boolean isAdmin(Principal principal);

	// User specified

	// Generic utility roles
	boolean isAdmin(String username, SsoClientContext context);

	boolean isClient(Principal principal);
	
}
