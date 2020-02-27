package org.einnovator.sso.client.manager;

import java.net.URI;
import java.util.Map;

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.config.SsoClientContext;
import org.einnovator.sso.client.model.Group;
import org.einnovator.sso.client.model.Member;
import org.einnovator.sso.client.modelx.GroupFilter;
import org.einnovator.sso.client.modelx.MemberFilter;
import org.einnovator.sso.client.modelx.UserOptions;
import org.einnovator.util.web.RequestOptions;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * High-level API for {@code Group}s remote operations.
 * 
 * @see Group
 * @see SsoClient
 * @author support@einnovator.org
 *
 */
public interface GroupManager {

	

	/**
	 * Get {@code Group} with specified identifier.
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID, name for root {@code Groups} if server is configured to required unique names for root {@code Group}s .
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param groupId the identifier
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code User} if found, or null if not found or request failed
	 */
	Group getGroup(String groupId, SsoClientContext context);

	/**
	 * Get {@code Group} with specified identifier.
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID, name for root {@code Groups} if server is configured to required unique names for root {@code Group}s .
	 * 
	 * <p><b>Required Security Credentials</b>: any for root {@code Group}, but results depend on each {@code User} privacy settings.
	 *
	 * @param groupId the identifier
	 * @param filter (optional) the {@code GroupOptions} that tailor which fields are returned (projection) and {@code GroupFilter} for sub-groups
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code User} if found, or null if not found or request failed
	 */
	Group getGroup(String groupId, GroupFilter filter, SsoClientContext context);

	/**
	 * List {@code Group}s.
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param filter a {@code GroupFilter}
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Group}s, or null if request failed
	 */	
	Page<Group> listGroups(GroupFilter filter, Pageable pageable, SsoClientContext context);

	/**
	 * Create a new {@code Group}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), any for root {@code Group}s. 
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param group the {@code Group}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code Group}, or null if request failed
	 */
	URI createGroup(Group group, RequestOptions options, SsoClientContext context);
	
	/**
	 * Update existing {@code Group}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN).
	 * <p>For root {@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}, owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param group the {@code Group}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the same {@code Group}, or null if request failed
	 */
	Group updateGroup(Group group, RequestOptions options, SsoClientContext context);

	/**
	 * Delete existing {@code Group}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN).
	 * <p>For root {@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}, owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param groupId the {@code Group} identifier
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return true if {@code Group} was deleted, or false if request failed
	 */
	boolean deleteGroup(String groupId, RequestOptions options, SsoClientContext context);

	
	//
	// Group Tree
	//
	
	/**
	 * List sub-{@code Group}s.
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param direct true if count only direct sub-group, false if count the all tree
	 * @param filter a {@code GroupFilter} to filter sub-groups
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Group}s, or null if request failed
	 */	
	Page<Group> listSubGroups(String groupId, boolean direct, GroupFilter filter, Pageable pageable, SsoClientContext context);

	/**
	 * Count {@code Group}s matching specified {@code GroupFilter}.
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param direct true if count only direct sub-group, false if count the all tree
	 * @param filter a {@code GroupFilter}
	 * @param context optional {@code SsoClientContext}
	 * @return the count of {@code Group}s, or null if request failed
	 */
	Integer countSubGroups(String groupId, boolean direct, GroupFilter filter, SsoClientContext context);

	//
	// Member
	//
	
	/**
	 * List {@code Member} of a {@code Group} .
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings,
	 * and each {@code User} privacy settings.
	 * 
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param filter a {@code MemberFilter}
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Member}s, or null if request failed
	 */	
	Page<Member> listMembers(String groupId, MemberFilter filter, Pageable pageable, SsoClientContext context);

	/**
	 * Get count of {@code Member} in a {@code Group} .
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings,
	 * and each {@code User} privacy settings.
	 * 
	 * @param groupId the {@code id} identifier (UUID, or name of root group if supported)
	 * @param filter a {@code MemberFilter}
	 * @param context optional {@code SsoClientContext}
	 * @return the count of {@code Member} (users), or null if request failed
	 */	
	Integer countMembers(String groupId, MemberFilter filter, SsoClientContext context);


	/**
	 * Get {@code Member} with specified identifier.
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID of {@code member}, of username od {@code User}
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings,
	 * and each {@code User} privacy settings.
	 * 
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param options optional {@code UserOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Member}, or null if request failed
	 */
	Member getMember(String groupId, String userId, UserOptions options, SsoClientContext context);

	/**
	 * Check if a user is memebr of a {@code Group}.
	 * 
	 * The member can be identified by UUID of the {@code Member}, of username of the {@code User}
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings,
	 * and each {@code User} privacy settings.
	 * 
	 *
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param options optional {@code UserOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Member}, or null if request failed
	 */
	boolean isMember(String userId, String groupId, UserOptions options, SsoClientContext context);


	/**
	 * Add user a new {@code Group}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), any for root {@code Group}s. 
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code Member}, or null if request failed
	 */
	URI addMember(String userId, String groupId, RequestOptions options, SsoClientContext context);


	/**
	 * Add user a new {@code Group}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), any for root {@code Group}s. 
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param member the {@code Member} to add to Group
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code Member}, or null if request failed
	 */
	public URI addMember(Member member, String groupId, RequestOptions options, SsoClientContext context);
	
	/**
	 * Remove {@code User} from a {@code Group}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), owner {@code User}.
	 * <p>For root {@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}, owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return true if {@code Member} was deleted, or false if request failed
	 */
	boolean removeMember(String userId, String groupId, RequestOptions options, SsoClientContext context);

	
	/**
	 * List {@code Group}s a {@code User} is member.
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings,
	 * and each {@code User} privacy settings.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param filter a {@code UserFilter} (optional)
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Group}s, or false if request failed
	 */	
	Page<Group> listGroupsForUser(String userId, GroupFilter filter, Pageable pageable, SsoClientContext context);

	
	//
	// Caching
	//

	/**
	 * Update cache entry for {@code Group}.
	 * 
	 * @param id the {@code Group} UUID
	 * @param details new state of {@code User}
	 * @param context optional {@code SsoClientContext}
	 */
	void onGroupUpdate(String id, Map<String, Object> details, SsoClientContext context);

	/**
	 * Update cache entry for {@code Member}.
	 * 
	 * @param id the {@code Member} UUID
	 * @param userId the {@code User} username or UUID
	 * @param context optional {@code SsoClientContext}
	 */
	void onGroupMemberUpdate(String id, String userId, SsoClientContext context);


	/**
	 * Clear all caches for {@code Group}s and {@code Member}s.
	 * 
	 */
	void clearCache();

	/**
	 * Get the cache for {@code User}s.
	 * 
	 * @return the {@code Cache}
	 */
	Cache getGroupCache();

	/**
	 * Get the cache for {@code Member}s.
	 * 
	 * @return the {@code Cache}
	 */
	Cache getGroupMembersCache();

	/**
	 * Get the cache to count {@code Member}s.
	 * 
	 * @return the {@code Cache}
	 */
	Cache getGroupMemberCountCache();

	/**
	 * Evict cache entries relevant to the event.
	 * 
	 * @param event the {@code ApplicationEvent}
	 */
	void onEvent(ApplicationEvent event);

	boolean isMember(String username, UserOptions options, SsoClientContext context, String... groups);

}
