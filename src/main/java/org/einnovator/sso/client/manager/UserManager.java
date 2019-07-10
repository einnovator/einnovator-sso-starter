package org.einnovator.sso.client.manager;


import java.net.URI;
import java.util.List;
import java.util.Map;

import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.UserFilter;
import org.einnovator.sso.client.modelx.UserOptions;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserManager {
	
	/**
	 * Get {@code User} with specified identifier.
	 * 
	 * Identifier {@code id} can be the value of properties that have unique constraints, that is:
	 * UUID, username, email.
	 * 
	 * @param id the indentifier
	 * @return the {@code User} if found, null otherwise.
	 */
	User getUser(String id);
	
	/**
	 * Get {@code User} with specified identifier.
	 * 
	 * Identifier {@code id} can be the value of properties that have unique constraints, that is:
	 * UUID, username, email.
	 * 
	 * @param id the identifier
	 * @param options (optional) the {@code UserOptions} that tailor which field set or projection to return.
	 * @return the {@code User} if found, null otherwise.
	 */
	User getUser(String id, UserOptions options);

	URI createUser(User user);
	
	User updateUser(User user, boolean fullState);

	User updateUser(User user);

	boolean deleteUser(String userId);
	
	Page<User> listUsers(UserFilter filter, Pageable options);
	
	void onUserUpdate(String id, Map<String, Object> details);

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
	 * @return the {@code Page} of {@code User}s  (or null if error)
	 */
	Page<User> listUsersWithPermissionsInGroups(List<String> groups, List<String> permissions, Pageable pageable);

	/**
	 * Get list of UUIDs of {@code Groups} that the {@code User} with specified identifier is member of.
	 * 
	 * @param id the {@code User} identifier
	 * @return the list of UUIDs (or null if error)
	 */
	List<String> getGroupsUuidForUser(String id);

}
