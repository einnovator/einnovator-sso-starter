package org.einnovator.sso.client.manager;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.config.SsoClientContext;
import org.einnovator.sso.client.model.Role;
import org.einnovator.sso.client.model.RoleBinding;
import org.einnovator.sso.client.modelx.RoleFilter;
import org.einnovator.sso.client.modelx.RoleOptions;
import org.einnovator.sso.client.modelx.UserFilter;
import org.einnovator.util.web.RequestOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * High-level API for {@code Role}s remote operations.
 * 
 * @see Role
 * @see SsoClient
 * @author support@einnovator.org
 *
 */
public interface RoleManager {

	/**
	 * Get {@code Role} with specified identifier.
	 * 
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN) for global Roles and group Roles prototypes. 
	 * <p>For root {@code Group}s: owner or role <b>PERMISSION_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or role <b>PERMISSION_MANAGER</b>, owner or role <b>PERMISSION_MANAGER</b> in parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 *
	 * @param id the {@code Role} identifier (UUID)
	 * @param options the {@code RoleOptions} (options)
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Role} if found, or null if not found or request failed
	 */
	Role getRole(String id, RoleOptions options, SsoClientContext context);

	
	/**
	 * List {@code Role}s.
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN) for global Roles and group Roles prototypes. 
	 * <p>For root {@code Group}s: owner or role <b>PERMISSION_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or role <b>PERMISSION_MANAGER</b>, owner or role <b>PERMISSION_MANAGER</b> in parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param filter a {@code RoleFilter}
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Role}s, or null if not found or request failed
	 */	
	Page<Role> listRoles(RoleFilter filter, Pageable pageable, SsoClientContext context);

	/**
	 * Create a new {@code Role}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN) for global Roles and group Roles prototypes. 
	 * <p>For root {@code Group}s: owner or role <b>PERMISSION_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or role <b>PERMISSION_MANAGER</b>, owner or role <b>PERMISSION_MANAGER</b> in parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param role the {@code Role}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code Role}, or null if request failed
	 */
	URI createRole(Role role, RequestOptions options, SsoClientContext context);

	/**
	 * Update existing {@code Role}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN) for global Roles and group Roles prototypes. 
	 * <p>For root {@code Group}s: owner or role <b>PERMISSION_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or role <b>PERMISSION_MANAGER</b>, owner or role <b>PERMISSION_MANAGER</b> in parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param role the {@code Role}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the same {@code Role}, or null if request failed
	 */
	Role updateRole(Role role, RequestOptions options, SsoClientContext context);

	/**
	 * Delete existing {@code Role}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN) for global Roles and group Roles prototypes. 
	 * <p>For root {@code Group}s: owner or role <b>PERMISSION_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or role <b>PERMISSION_MANAGER</b>, owner or role <b>PERMISSION_MANAGER</b> in parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param id the {@code Role} identifier (UUID)
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return true if {@code Role} was deleted, or false if request failed
	 */
	boolean deleteRole(String id, RequestOptions options, SsoClientContext context);

	
	//
	// Role Bindings/Assignments
	//
	
	/**
	 * List {@code RoleBinding}s assigned a {@code Role} .
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN) for global Roles and group Roles prototypes. 
	 * <p>For root {@code Group}s: owner or role <b>PERMISSION_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or role <b>PERMISSION_MANAGER</b>, owner or role <b>PERMISSION_MANAGER</b> in parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param roleId the {@code Role} identifier (UUID)
	 * @param filter a {@code UserFilter} (optional)
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code User}s, or null if not found or request failed
	 */		
	Page<RoleBinding> listRoleBindings(String roleId, UserFilter filter,  Pageable pageable, SsoClientContext context);

	/**
	 * Get count of {@code User}s assigned a {@code Role} .
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN) for global Roles and group Roles prototypes. 
	 * <p>For root {@code Group}s: owner or role <b>PERMISSION_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or role <b>PERMISSION_MANAGER</b>, owner or role <b>PERMISSION_MANAGER</b> in parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param roleId the {@code Role} identifier (UUID)
	 * @param filter a {@code UserFilter} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code User} count, or null if not found or request failed
	 */
	Integer countRoleBindings(String roleId, UserFilter filter, SsoClientContext context);

	/**
	 * Assign {@code Role} to {@code User}
	 * 
	 * Request ignored if {@code User} is already assigned the {@code Role}.
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), any for root {@code Group}s. 
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param roleId the {@code Role} identifier (UUID)
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return true if {@code Role} was assigned, or false if request failed
	 */
	boolean assignRole(String userId, String roleId,  RequestOptions options, SsoClientContext context);

	/**
	 * Unassign {@code Role} from {@code User}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), owner {@code User}.
	 * <p>For root {@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}, owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param roleId the {@code Role} identifier (UUID)
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return true if {@code Role} was unassigned, or false if request failed
	 */
	boolean unassignRole(String userId, String roleId,  RequestOptions options, SsoClientContext context);

	
	/**
	 * List global {@code Role}s a {@code User} is assigned to.
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), owner {@code User}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param filter a {@code RoleFilter} (optional)
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Role}s, or false if request failed
	 */
	Page<Role> listRolesForUser(String userId, RoleFilter filter, Pageable pageable, SsoClientContext context);

	/**
	 * List {@code Role}s a {@code User} is assigned to in a {@code Group}.
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), owner {@code User}.
	 * <p>For root {@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}, owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param groupId the identifier of a {@code Group} (UUID)
	 * @param filter a {@code RoleFilter} (optional)
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Role}s, or false if request failed
	 */	
	Page<Role> listRolesForUserInGroup(String userId, String groupId, RoleFilter filter, Pageable pageable, SsoClientContext context);

	/**
	 * List {@code Role}s bound to a {@code Group}.
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), owner {@code User}.
	 * <p>For root {@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}, owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param groupId the identifier of a {@code Group} (UUID)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Role}s, or false if request failed
	 */	
	List<Role> listRolesForGroup(String groupId, SsoClientContext context);

	//
	// Local Authority Checks
	//

	/**
	 * Check if {@code Principal}  has any of the specified (global) {@code Roles}.
	 * 
	 * <p>Roles are identified by name.
	 * <p>This is a local operation with authorities retrieved from the  {@code SecurityContext}.
	 * 
	 * @param principal the {@code Principal}
	 * @param roles a variadic array of roles
	 * @return true if {@code Principal} has the role, false otherwise.
	 */
	boolean hasAnyRole(Principal principal, String... roles);

	/**
	 * Check if {@code Principal}  has any of the specified (global) {@code Roles}.
	 * 
	 * <p>Roles are identified by name.
	 * <p>This is a local operation with authorities retrieved from the  {@code SecurityContext}.
	 * 	  
	 * @param roles a variadic array of roles
	 * @return true if {@code Principal} has the role, false otherwise.
	 */
	boolean hasAnyRole(String... roles);
	
	/**
	 * Check if {@code Principal}  has any of the specified {@code Role}s in a {@code Group}.
	 * 
	 * <p>Roles are identified by name.
	 * <p>This is a local operation with authorities retrieved from the  {@code SecurityContext}.
	 * 
	 * @param principal the {@code Principal}
	 * @param groupId the group UUID
	 * @param roles a variadic array of roles
	 * @return true if {@code Principal} has the role, false otherwise.
	 */
	boolean hasAnyRoleInGroup(Principal principal, String groupId, String... roles);


	/**
	 * Check if {@code Principal}  has any of the specified {@code Role}s in a {@code Group}.
	 * 
	 * <p>Roles are identified by name.
	 * <p>This is a local operation with authorities retrieved from the  {@code SecurityContext}.
	 * 
	 * @param groupId the group UUID
	 * @param roles a variadic array of roles
	 * @return true if {@code Principal} has the role, false otherwise.
	 */
	boolean hasAnyRoleInGroup(String groupId, String... roles);
	
	/**
	 * Check if {@code Principal} has <b>ADMIN</b> role.
	 * 
	 * @param principal the {@code Principal}
	 * @return true if admin, false otherwise
	 */
	boolean isAdmin(Principal principal);

	/**
	 * Check if {@code Principal} has <b>CLIENT</b> role.
	 * 
	 * @param principal the {@code Principal}
	 * @return true if client, false otherwise
	 */
	boolean isClient(Principal principal);

	
	/**
	 * Check if a named {@code User}  has any of the specified (global) {@code Role}s.
	 * 
	 * <p>Roles are identified by name.
	 * <p>This is a local operation with authorities retrieved from the  {@code SecurityContext}.
	 *  
	 * @param username the {@code username}
	 * @param context optional {@code SsoClientContext}
	 * @param roles a variadic array of roles
	 * @return true if {@code Principal} has the role, false otherwise.
	 */
	boolean hasAnyRoleUser(String username, SsoClientContext context, String... roles);

	
	
	//
	// Remote Authority Checks
	//


	/**
	 * Check if a named {@code User}  has any of the specified {@code Role}s  in a {@code Group}.
	 * 
	 * <p>Roles are identified by name.
	 * <p>This is a remote operation.
	 * 
	 * @param username the {@code username}
	 * @param groupId the group UUID
	 * @param context optional {@code SsoClientContext}
	 * @param roles a variadic array of roles
	 * @return true if {@code Principal} has the role, false otherwise.
	 */
	boolean hasAnyRoleUserInGroup(String username, String groupId, SsoClientContext context, String... roles);

	
	/**
	 * Check if specified {@code User} has <b>ADMIN</b> role.
	 * 
	 * @param username the username
	 * @param context optional {@code SsoClientContext}
	 * @return true if admin, false otherwise
	 */
	boolean isAdmin(String username, SsoClientContext context);


	
	
}
