package org.einnovator.sso.client.manager;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.sso.client.SsoClient;

import org.einnovator.sso.client.model.Member;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class UserManagerImpl extends ManagerBase implements UserManager {

	public static final String CACHE_USER = "User";

	private final Log logger = LogFactory.getLog(getClass());
	
	private CacheManager cacheManager;
	
	@Autowired
	private SsoClient ssoClient;
	
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
		String principalName = SecurityUtil.getPrincipalName();
		if (id==null || principalName==null || SecurityUtil.isAnonymous()) {
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
				logger.error("getUser:" + id + "  " + options + e);				
			}
			return null;
		} catch (RuntimeException e) {
			logger.error("getUser:" + id + "  " + options + " " + e);				
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
			logger.error("createUser:" + e);
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
			logger.error("updateUser:" + e);
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
	public List<String> getGroupsUuidForUser(String username) {
		if (username==null) {
			return null;
		}
		User user = getUser(username, UserOptions.FULL);
		return user!=null ? user.getGroupsUuid() : null;
	}

}
