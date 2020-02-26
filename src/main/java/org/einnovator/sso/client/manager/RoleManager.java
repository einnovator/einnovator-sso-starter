package org.einnovator.sso.client.manager;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import org.einnovator.sso.client.config.SsoClientContext;
import org.einnovator.sso.client.model.Role;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.RoleFilter;
import org.einnovator.sso.client.modelx.RoleOptions;
import org.einnovator.sso.client.modelx.UserFilter;
import org.einnovator.sso.client.modelx.UserOptions;
import org.einnovator.util.web.RequestOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleManager {

	Role getRole(String roleId, RoleOptions options, SsoClientContext context);

	Page<Role> listRoles(RoleFilter filter, Pageable pageable, SsoClientContext context);

	URI createRole(Role role, RequestOptions options, SsoClientContext context);

	void updateRole(Role role, RequestOptions options, SsoClientContext context);

	void deleteRole(String roleId, RequestOptions options, SsoClientContext context);

	
	Page<User> listRoleMembers(String roleId, UserFilter filter,  Pageable pageable, SsoClientContext context);

	Integer countRoleMembers(String roleId, UserFilter filter, SsoClientContext context);

	void assignRole(String username, String roleId,  RequestOptions options, SsoClientContext context);

	void unassignRole(String username, String roleId,  RequestOptions options, SsoClientContext context);

	
	Page<Role> listRolesForUser(String userId, RoleFilter filter, Pageable pageable, SsoClientContext context);

	Page<Role> listRolesForUserInGroup(String userId, String groupId, RoleFilter filter, Pageable pageable, SsoClientContext context);

	List<Role> listRolesForGroup(String groupId, SsoClientContext context);

	boolean hasAnyRoleUser(String username, SsoClientContext context, String... role);

	boolean hasAnyRoleUserInGroup(String username, String groupId, SsoClientContext context, String... roles);

	boolean hasAnyRole(String... roles);

	boolean hasAnyRole(Principal principal, String... roles);

	boolean hasAnyRoleInGroup(Principal principal, String groupId, String... roles);

	boolean hasAnyRoleInGroup(String groupId, String... roles);

	boolean isMember(String username, UserOptions options, SsoClientContext context, String... groups);

	
	// Generic utility roles
	boolean isAdmin(Principal principal);

	// User specified

	// Generic utility roles
	boolean isAdmin(String username, SsoClientContext context);

	boolean isClient(Principal principal);

	
	
}
