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
	
	User getUser(String id);
	
	User getUser(String id, UserOptions options);

	URI createUser(User user);
	
	void updateUser(User user, boolean fullState);

	void updateUser(User user);

	void deleteUser(String userId);
	
	Page<User> listUsers(UserFilter filter, Pageable options);
	
	void onUserUpdate(String id, Map<String, Object> details);

	void clearCache();
	
	Cache getUserCache();

	void onEvent(ApplicationEvent event);

	/**
	 * @param userId
	 */
	void evictCaches(String userId);

	/**
	 * @param groups
	 * @param permissions
	 * @param pageable
	 * @return
	 */
	Page<User> listUsersWithPermissionsInGroups(List<String> groups, List<String> permissions, Pageable pageable);

}
