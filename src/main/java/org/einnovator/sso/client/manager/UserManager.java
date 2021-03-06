package org.einnovator.sso.client.manager;


import java.net.URI;
import java.util.List;
import java.util.Map;

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.model.Group;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.UserFilter;
import org.einnovator.sso.client.modelx.UserOptions;
import org.einnovator.util.web.RequestOptions;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.client.RestClientException;

/**
 * High-level API for {@code User}s remote operations.
 * 
 * @see User
 * @see SsoClient
 * @author support@einnovator.org
 *
 */
public interface UserManager {
	
	/**
	 * Get {@code User} with specified identifier using local information.
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID, username, email.
	 * 
	 * <p><b>Required Security Credentials</b>: Any, but results depend on each {@code User} privacy settings.
	 * 
	 * @param id the {@code User} uuid or username
	 * @param remote if true fallback to remote lookup
	 * @return the {@code User} if found, or null if not found or request failed
	 */
	User getLocalUser(String id, boolean remote);
	
	/**
	 * Get {@code User} with specified identifier.
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID, username, email.
	 * 
	 * <p><b>Required Security Credentials</b>: Any, but results depend on each {@code User} privacy settings.
	 * 
	 * @param id the {@code User} uuid or username
	 * @return the {@code User} if found, or null if not found or request failed
	 */
	User getUser(String id);
	
	
	/**
	 * Get {@code User} with specified identifier.
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID, username, email.
	 * 
	 * <p><b>Required Security Credentials</b>: Any, but results depend on each {@code User} privacy settings.
	 * 
	 * @param id the identifier
	 * @param options (optional) the {@code UserOptions} that tailor which fields are returned (projection)
	 * @return the {@code User} if found, or null if not found or request failed
	 */
	User getUser(String id, UserOptions options);
	
	/**
	 * Check if {@code User} with specified identifier exists.
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID, username, email.
	 * 
	 * <p><b>Required Security Credentials</b>: Any, but results depend on each {@code User} privacy settings.
	 * 
	 * @param id the identifier
	 * @param options (optional) {@code RequestOptions}
	 * @return true if user exists, false if user does not exist, null if request failed.
	 */
	public Boolean checkUser(String id, RequestOptions options);
	
	/**
	 * Get temporary login token for {@code User} with specified identifier.
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID, username, email.
	 * 
	 * <p><b>Required Security Credentials</b>: Admin or authorized client.
	 * 
	 * @param id the identifier
	 * @param options (optional) {@code RequestOptions}
	 * @return the token
	 */
	public String loginUser(String id, RequestOptions options);
	
	/**
	 * List {@code User}s.
	 * 
	 * <p><b>Required Security Credentials</b>: Any, but results depend on credentials and each {@code User} privacy settings.
	 * 
	 * @param filter a {@code UserFilter}
	 * @param pageable a {@code Pageable} (optional)

	 * @throws RestClientException if request fails
	 * @return a {@code Page} with {@code User}s, or null if request failed
	 */
	Page<User> listUsers(UserFilter filter, Pageable pageable);
	

	/**
	 * Create a new {@code User}
	 * 
	 * 
	 * <p><b>Required Security Credentials</b>: Client or Admin (global role ADMIN).
	 * 
	 * @param user the {@code User}
	 * @param options optional {@code UserOptions}
	 * @return the location {@code URI} for the created {@code User}, or null if request failed
	 */
	URI createUser(User user, UserOptions options);
	
	/**
	 * Update existing {@code User}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), or owner.
	 * 
	 * @param user the {@code User}
	 * @param options optional {@code UserOptions}
	 * @return the same {@code User}, or null if request failed
	 */
	User updateUser(User user, UserOptions options);

	/**
	 * Delete existing {@code User}
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID, username, email.
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), or owner.
	 * 
	 * @param userId the {@code User} identifier
	 * @param options optional {@code RequestOptions}
	 * @return true if {@code User} was deleted, or false if request failed
	 */
	boolean deleteUser(String userId, UserOptions options);
	
	
	//
	// Caching
	//
	
	/**
	 * Update cache entry for {@code User}.
	 * 
	 * @param id the {@code User} uuid or username
	 * @param details new state of {@code User}

	 */
	void onUserUpdate(String id, Map<String, Object> details);

	/**
	 * Clear the cache for {@code User}s.
	 * 
	 */
	void clearCache();

	/**
	 * Get the cache for {@code User}s.
	 * 
	 * @return the {@code Cache}
	 */
	Cache getUserCache();


	/**
	 * Handle {@code ApplicationEvent}.
	 * 
	 * Updates the {@code User}s in cache if event of relevant type.
	 * 
	 * @param event an {@code ApplicationEvent}
	 */
	void onEvent(ApplicationEvent event);

	/**
	 * Evict cache entries relevant to the event.
	 * 
	 * @param id the user uuid or username
	 */
	void evictCaches(String id);

	
	//
	// Other
	//
	
	/**
	 * Get list of UUIDs of {@code Groups} that the {@code User} with specified username is member of.
	 * 
	 * @param id the {@code User} identifier
	 * @param local use local data (e.g. principal info in security context or cached information)
	 * @param remote fallback to remote server
	 * @return the list of UUIDs (or null if error)
	 */
	List<String> getGroupsUuidForUser(String id, boolean local, boolean remote);

	/**
	 * Get list of {@code Groups} that the {@code User} with specified username is member of.
	 * 
	 * @param username the {@code User} username
	 * @param local use local data (e.g. principal info in security context or cached information)
	 * @param remote fallback to remote server
	 * @return the list of UUIDs (or null if error)
	 */
	List<Group> getGroupsForUser(String username, boolean local, boolean remote);

	/**
	 * Make a {@code UserManager} with same configuration and setup with client authorities.
	 * 
	 * @return the {@code UserManager}
	 */
	UserManager makeAsClient();

	/**
	 * Get {@code User} with specified identifier with client credentials.
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID, username, email.
	 * 
	 * <p><b>Required Security Credentials</b>: Any, but results depend on each {@code User} privacy settings.
	 * 
	 * @param id the identifier
	 * @param options (optional) the {@code UserOptions} that tailor which fields are returned (projection)
	 * @return the {@code User} if found, or null if not found or request failed
	 */
	User getUserAsClient(String id, UserOptions options);

}
