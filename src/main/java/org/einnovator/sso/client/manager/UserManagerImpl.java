package org.einnovator.sso.client.manager;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.model.Group;
import org.einnovator.sso.client.model.Member;
import org.einnovator.sso.client.model.Role;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.UserFilter;
import org.einnovator.sso.client.modelx.UserOptions;
import org.einnovator.util.security.SecurityUtil;
import org.einnovator.util.web.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class UserManagerImpl extends ManagerBase implements UserManager {

	public static final String CACHE_USER = "User";
	public static final Pageable PAGE_SIZE_ALL = new PageRequest(0, Integer.MAX_VALUE);

	private final Log logger = LogFactory.getLog(getClass());
	
	private CacheManager cacheManager;
	
	@Autowired
	private SsoClient ssoClient;
	
	@Autowired
	private GroupManager groupManager;
	
	public UserManagerImpl(SsoClient ssoClient, CacheManager cacheManager) {
		this.ssoClient = ssoClient;
		this.cacheManager = cacheManager;
	}
	
	public UserManagerImpl(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public User getUser(String id) {
		return getUser(id, UserOptions.DEFAULT);
	}

	@Override
	public User getLocalUser(String id, boolean remote) {
		if (id==null) {
			if (SecurityUtil.isAnonymous()) {
				return null;
			}
			id = SecurityUtil.getPrincipalName();
		}
		if (id==null) {
			return null;
		}
		String principalName = SecurityUtil.getPrincipalName();
		if (principalName==null || SecurityUtil.isAnonymous()) {
			return null;
		}
		if (id.equals(principalName)) {
			return SsoClient.getPrincipalUser();
		}
		if (remote) {
			return getUser(id);								
		}
		return null;
	}
	
	@Override
	public User getUser(String id, UserOptions options) {
		if (id==null) {
			return null;
		}
		if (cacheable(options)) {
			User user = getCacheValue(User.class, getUserCache(), id, options);
			if (user!=null) {
				return user;
			}			
		}
		try {
			User user = ssoClient.getUser(id, options);	
			if (cacheable(options)) {
				return putCacheValue(user, getUserCache(), id, options);				
			}
			return user;
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode()!=HttpStatus.NOT_FOUND) {
				if (options==null || !options.isSilent()) {
					logger.error(String.format("getUser: %s %s %s", e, id, options));
				}
			}
			return null;
		} catch (RuntimeException e) {
			if (options==null || !options.isSilent()) {
				logger.error(String.format("getUser: %s %s %s", e, id, options));								
			}
			return null;
		}

	}

	protected boolean cacheable(UserOptions options) {
		return options==null || UserOptions.FULL.equals(options) || UserOptions.ORGS.equals(options)  ||
				UserOptions.ORGS_OPS.equals(options) || UserOptions.ORGS_OPS_TEAMS.equals(options);
	}
	
	@Override
	public URI createUser(User user, RequestOptions options) {
		try {
			return ssoClient.createUser(user, options);
		} catch (RuntimeException e) {
			if (options==null || !options.isSilent()) {
				logger.error(String.format("createUser: %s", e));								
			}
			return null;
		}
	}
	

	@Override
	public User updateUser(User user, RequestOptions options) {
		try {
			ssoClient.updateUser(user, options);
			evictCaches(user.getUuid());
			return user;
		} catch (RuntimeException e) {
			if (!options.isSilent()) {
				logger.error(String.format("updateUser: %s", e));								
			}
			return null;
		}
	}

	
	@Override
	@CacheEvict(value=CACHE_USER, key="#id")
	public boolean deleteUser(String userId, RequestOptions options) {
		try {
			ssoClient.deleteUser(userId, options);
			return true;
		} catch (RuntimeException e) {
			logger.error("deleteUser:" + e);
			return false;
		}
	}
	
	
	@Override
	public Page<User> listUsers(UserFilter filter, Pageable pageable) {
		try {
			return ssoClient.listUsers(filter, pageable);
		} catch (RuntimeException e) {
			logger.error("listUsers:" + e);
			return null;
		}
	}


	@Override
	public void onUserUpdate(String id, Map<String, Object> details) {
		if (id==null) {
			return;
		}
		try {
			Cache cache = getUserCache();
			if (cache!=null) {
				ValueWrapper e =  cache.get(id);
				if (e!=null) {
					User user = (User)e.get();
					if (details!=null) {
						if (user!=null) {					
							user.updateFrom(details);	
						}
					} else {
						cache.evict(id);
					}
				}
			}
		} catch (RuntimeException e) {
			logger.error("onUserUpdate: " + e + " " + id);
		}
	}

	@Override
	public Cache getUserCache() {
		Cache cache = cacheManager.getCache(UserManagerImpl.CACHE_USER);
		return cache;
	}
	
	@Override
	public void clearCache() {
		Cache cache = getUserCache();
		if (cache!=null) {
			cache.clear();
		}
	}

	public static final String ACTION_TYPE_LOGOUT = "Logout";

	@EventListener
	@Override
	public void onEvent(ApplicationEvent event) {
		if (!(event instanceof PayloadApplicationEvent)) {
			return;
		}
		Object obj = ((PayloadApplicationEvent<?>)event).getPayload();
		User user= getNotificationSource(obj, User.class);
		logger.debug("onEvent:" + user + " " + obj);
		if (user!=null) {
			evictCaches(user);
			return;
		}
		Member member = getNotificationSource(obj, Member.class);
		if (member!=null && member.getUser()!=null) {
			logger.debug("onEvent:" + member + " " + obj);
			evictCaches(member.getUser());				
		}
		
	}

	private void evictCaches(User user) {
		if (user!=null) {
			evictCaches(user.getUsername());
			evictCaches(user.getUuid());
		}
	}
	
	@Override
	public void evictCaches(String userId) {
		Cache cache = getUserCache();
		if (cache != null && userId != null) {
			cache.evict(userId);
			cache.evict(makeKey(userId, UserOptions.FULL));
			cache.evict(makeKey(userId, UserOptions.ORGS));
			cache.evict(makeKey(userId, UserOptions.ORGS_OPS));
			cache.evict(makeKey(userId, UserOptions.ORGS_OPS_TEAMS));
		}
	}

	@Override
	public List<String> getGroupsUuidForUser(String username, boolean local, boolean remote) {
		List<Group> groups = getGroupsForUser(username, local, false);
		if (groups!=null) {
			return Group.getUuids(groups);
		}
		if (username==null) {
			if (SecurityUtil.isAnonymous()) {
				return null;
			}
			username = SecurityUtil.getPrincipalName();
		}
		if (username==null) {
			return null;
		}
		if (local) {
			String principalName = SecurityUtil.getPrincipalName();
			if (principalName!=null && username.equals(principalName)) {
				List<String> ids = Role.getGroups(SecurityUtil.getAuthorities());
				if (ids!=null) {
					return ids;
				}
			}			
		}
		if (remote) {
			groups = getGroupsForUser(username, false, true);
			if (groups!=null) {
				return Group.getUuids(groups);
			}
		}
		return null;
	}


	@Override
	public List<Group> getGroupsForUser(String username, boolean local, boolean remote) {
		if (username==null) {
			if (SecurityUtil.isAnonymous()) {
				return null;
			}
			username = SecurityUtil.getPrincipalName();
		}
		if (username==null) {
			return null;
		}
		if (local) {
			User user = getLocalUser(username, false);
			if (user!=null) {
				return Member.getGroups(user.getMembership());
			}
		}
		if (remote) {
			Page<Group> groups = groupManager.listGroupsForUser(username, null, PAGE_SIZE_ALL);
			if (groups!=null) {
				return groups.getContent();
			}
		}
		return null;
	}

}
