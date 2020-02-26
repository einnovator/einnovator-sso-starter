package org.einnovator.sso.client.manager;


import java.net.URI;
import java.util.List;
import java.util.Map;

import org.einnovator.sso.client.config.SsoClientContext;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.UserFilter;
import org.einnovator.sso.client.modelx.UserOptions;
import org.einnovator.util.web.RequestOptions;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserManager {
	
	/**
	 * Get {@code User} with specified identifier.
	 * 
	 * Identifier {@code id} is the value of property with unique constraints, that is:
	 * UUID, username, email.
	 * 
	 * @param id the identifier
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code User} if found, null otherwise.
	 */
	User getUser(String id, SsoClientContext context);
	
	/**
	 * Get {@code User} with specified identifier.
	 * 
	 * Identifier {@code id} is the value of property with unique constraints, that is:
	 * UUID, username, email.
	 * 
	 * @param id the identifier
	 * @param options (optional) the {@code UserOptions} that tailor which fields are returned (projection)
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code User} if found, null otherwise or if request failed.
	 */
	User getUser(String id, UserOptions options, SsoClientContext context);

	/**
	 * Create a new {@code User}
	 * 
	 * 
	 * <p><b>Required Security Credentials</b>: Client or Admin (global ROLE_ADMIN).
	 * 
	 * @param user the {@code User}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code User}, or null if request failed.
	 */
	URI createUser(User user, RequestOptions options, SsoClientContext context);
	
	/**
	 * Update existing {@code User}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), or owner.
	 * 
	 * @param user the {@code User}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the same {@code User}, or null if request failed.
	 */
	User updateUser(User user, RequestOptions options, SsoClientContext context);

	boolean deleteUser(String userId, RequestOptions options, SsoClientContext context);
	
	Page<User> listUsers(UserFilter filter, Pageable options, SsoClientContext context);
	
	void onUserUpdate(String id, Map<String, Object> details, SsoClientContext context);

	void clearCache();
	
	Cache getUserCache();

	void onEvent(ApplicationEvent event);

	/**
	 * Evict chache for {@code User} with specified identifier
	 * 
	 * @param id the identifier
	 */
	void evictCaches(String id);

	/**
	 * List {@code User}s with {@code Role}s that have any of the named {@code Permission}s in the at least one of the specified {@code Group}s.
	 * 
	 * @param groups list of {@code Group} identifiers
	 * @param permissions list of {@code Permission} names
	 * @param pageable a {@code Pageable}
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Page} of {@code User}s  (or null if error)
	 */
	Page<User> listUsersWithPermissionsInGroups(List<String> groups, List<String> permissions, Pageable pageable, SsoClientContext context);

	/**
	 * Get list of UUIDs of {@code Groups} that the {@code User} with specified identifier is member of.
	 * 
	 * @param id the {@code User} identifier
	 * @param context TODO
	 * @return the list of UUIDs (or null if error)
	 */
	List<String> getGroupsUuidForUser(String id, SsoClientContext context);

}
