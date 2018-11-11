package org.einnovator.sso.client.manager;

import java.net.URI;
import java.util.Map;

import org.apache.log4j.Logger;
import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.UserFilter;
import org.einnovator.sso.client.modelx.UserOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

@CacheConfig(cacheManager="ssoCacheManager", cacheResolver="ssoCacheResolver")
public class UserManagerImpl implements UserManager {

	public static final String CACHE_USER = "User";

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SsoClient client;
	
	private CacheManager cacheManager;
	
	public UserManagerImpl(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	@Override
	@Cacheable(value=CACHE_USER, key="#id", cacheManager="ssoCacheManager")
	public User getUser(String id) {
		try {
			User user = client.getUser(id);		
			if (user==null) {
				logger.error("getUser" + id);
			}
			return user;
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode()!=HttpStatus.NOT_FOUND) {
				logger.error("getUser:" + id + "  " + e);				
			}
			return null;
		} catch (RuntimeException e) {
			logger.error("getUser:" + id + "  " + e);
			return null;
		}
	}

	@Override
	//@Cacheable(value=CACHE_USER, key="#id + ' ' + #options.orgs + ' ' + #options.ops + ' ' + #options.teams + ' ' + #options.roles + ' ' + #options.permissions", cacheManager="ssoCacheManager")
	public User getUser(String id, UserOptions options) {
		try {
			User user = client.getUser(id, options);		
			if (user==null) {
				logger.error("getUser" + id);
			}
			return user;
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode()!=HttpStatus.NOT_FOUND) {
				logger.error("getUser:" + id + "  " + options + " " +  e);				
			}
			return null;
		} catch (RuntimeException e) {
			logger.error("getUser:" + id + "  " + options + " " + e);				
			return null;
		}
	}

	@Override
	public URI createUser(User user) {
		try {
			return client.createUser(user);
		} catch (RuntimeException e) {
			logger.error("createUser:" + e);
			return null;
		}
	}
	
	@Override
	@CachePut(value=CACHE_USER, key="#user.id", cacheManager="ssoCacheManager")
	public void updateUser(User user, boolean fullState) {
		try {
			client.updateUser(user);
		} catch (RuntimeException e) {
			logger.error("updateUser:" + e);
		}
	}
	
	@Override
	@CachePut(value=CACHE_USER, key="#user.id", cacheManager="ssoCacheManager")
	public void updateUser(User user) {
		try {
			client.updateUser(user);
		} catch (RuntimeException e) {
			logger.error("updateUser:" + e);
		}
	}
	
	@Override
	@CacheEvict(value=CACHE_USER, key="#id", cacheManager="ssoCacheManager")
	public void deleteUser(String userId) {
		try {
			client.deleteUser(userId);
		} catch (RuntimeException e) {
			logger.error("deleteUser:" + e);
		}
	}
	
	
	@Override
	public Page<User> listUsers(UserFilter filter, Pageable pageable) {
		try {
			return client.listUsers(filter, pageable);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("listUsers:" + e);
			return null;
		}
	}
	
	
	public void onUserUpdate(String id, Map<String, Object> details) {
		if (id==null) {
			return;
		}
		try {
			Cache cache = getUserCache();
			if (cache!=null) {
				User user = (User) cache.get(id);
				if (user!=null) {
					if (details!=null) {
						user.updateFrom(details);						
					} else {
						cache.evict(id);
					}
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("onUserUpdate: " + e);
		}
	}

	@Override
	public void clearCache() {
		Cache cache = getUserCache();
		if (cache!=null) {
			cache.clear();
		}
	}

	@Override
	public Cache getUserCache() {
		Cache cache = cacheManager.getCache(UserManagerImpl.CACHE_USER);
		return cache;
	}
	
}
