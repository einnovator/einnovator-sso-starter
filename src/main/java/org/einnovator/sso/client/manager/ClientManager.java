package org.einnovator.sso.client.manager;


import java.net.URI;
import java.util.Map;

import org.einnovator.sso.client.config.SsoClientContext;
import org.einnovator.sso.client.model.Client;
import org.einnovator.sso.client.modelx.ClientFilter;
import org.einnovator.sso.client.modelx.ClientOptions;
import org.einnovator.util.web.RequestOptions;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientManager {
	
	Client getClient(String id, SsoClientContext context);
	
	Client getClient(String id, ClientOptions options, SsoClientContext context);

	URI createClient(Client client, RequestOptions options, SsoClientContext context);
	
	Client updateClient(Client client, RequestOptions options, SsoClientContext context);

	boolean deleteClient(String clientId, RequestOptions options, SsoClientContext context);
	
	Page<Client> listClients(ClientFilter filter, Pageable options, SsoClientContext context);
	
	
	void onClientUpdate(String id, Map<String, Object> details, SsoClientContext context);

	void clearCache();
	
	Cache getClientCache();

	void onEvent(ApplicationEvent event);

	void evictCaches(String clientId);

}
