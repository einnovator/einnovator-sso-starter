package org.einnovator.sso.client.manager;

import java.net.URI;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.sso.client.SsoClient;

import org.einnovator.sso.client.model.Client;
import org.einnovator.sso.client.modelx.ClientFilter;
import org.einnovator.sso.client.modelx.ClientOptions;
import org.einnovator.util.web.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class ClientManagerImpl extends ManagerBase implements ClientManager {

	public static final String CACHE_CLIENT = "Client";

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private SsoClient ssoClient;

	public ClientManagerImpl(SsoClient ssoClient, CacheManager cacheManager) {
		this.ssoClient = ssoClient;
		this.cacheManager = cacheManager;
	}
	
	public ClientManagerImpl(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public ClientManagerImpl() {
	}
	
	@Override
	public Client getClient(String id) {
		return getClient(id, null);
	}

	@Override
	public Client getClient(String id, ClientOptions options) {
		if (id==null) {
			return null;
		}
		if (isCacheable(options)) {
			Client client = getCacheValue(Client.class, getClientCache(), id, options);
			if (client!=null) {
				return client;
			}			
		}
		try {
			Client client = ssoClient.getClient(id, options);	
			if (isCacheable(options)) {
				return putCacheValue(client, getClientCache(), id, options);				
			}
			return client;
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode()!=HttpStatus.NOT_FOUND) {
				logger.error("getClient:" + id + "  " + options + e);				
			}
			return null;
		} catch (RuntimeException e) {
			logger.error("getClient:" + id + "  " + options + " " + e);				
			return null;
		}

	}

	protected boolean isCacheable(ClientOptions options) {
		return options==null || ClientOptions.DEFAULT.equals(options);
	}
	
	@Override
	public URI createClient(Client client, RequestOptions options) {
		try {
			return ssoClient.createClient(client, options);
		} catch (RuntimeException e) {
			logger.error("createClient:" + e);
			return null;
		}
	}
	

	@Override
	public Client updateClient(Client client, RequestOptions options) {
		try {
			ssoClient.updateClient(client, options);
			evictCaches(client.getUuid());
			return client;
		} catch (RuntimeException e) {
			logger.error("updateClient:" + e);
			return null;
		}
	}
	
	@Override
	public boolean deleteClient(String clientId, RequestOptions options) {
		try {
			ssoClient.deleteClient(clientId, options);
			evictCaches(clientId);
			return true;
		} catch (RuntimeException e) {
			logger.error("deleteClient:" + e);
			return false;
		}
	}
	
	
	@Override
	public Page<Client> listClients(ClientFilter filter, Pageable pageable) {
		try {
			return ssoClient.listClients(filter, pageable);
		} catch (RuntimeException e) {
			logger.error("listClients:" + e);
			return null;
		}
	}


	@Override
	public void onClientUpdate(String id, Map<String, Object> details) {
		if (id==null) {
			return;
		}
		try {
			Cache cache = getClientCache();
			if (cache!=null) {
				ValueWrapper e =  cache.get(id);
				if (e!=null) {
					Client client = (Client)e.get();
					if (details!=null) {
						if (client!=null) {					
							client.updateFrom(details);	
						}
					} else {
						cache.evict(id);
					}
				}
			}
		} catch (RuntimeException e) {
			logger.error("onClientUpdate: " + e);
		}
	}

	@Override
	public Cache getClientCache() {
		Cache cache = cacheManager.getCache(ClientManagerImpl.CACHE_CLIENT);
		return cache;
	}
	
	@Override
	public void clearCache() {
		Cache cache = getClientCache();
		if (cache!=null) {
			cache.clear();
		}
	}

	@EventListener
	@Override
	public void onEvent(ApplicationEvent event) {
		if (!(event instanceof PayloadApplicationEvent)) {
			return;
		}
		Object obj = ((PayloadApplicationEvent<?>)event).getPayload();
		Client client= getNotificationSource(obj, Client.class);
		if (logger.isDebugEnabled()) {
			logger.debug("onEvent:" + client + " " + obj);			
		}
		if (client!=null) {
			evictCaches(client);
			return;
		}
	}

	private void evictCaches(Client client) {
		if (client!=null) {
			evictCaches(client.getUuid());
		}
	}
	
	@Override
	public void evictCaches(String clientId) {
		Cache cache = getClientCache();
		if (cache != null && clientId != null) {
			cache.evict(makeKey(clientId));
			cache.evict(makeKey(clientId, ClientOptions.DEFAULT));
		}
	}



}
