package org.einnovator.sso.client.manager;


import java.net.URI;
import java.util.List;
import java.util.Map;

import org.einnovator.sso.client.model.Client;
import org.einnovator.sso.client.modelx.ClientFilter;
import org.einnovator.sso.client.modelx.ClientOptions;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientManager {
	
	Client getClient(String id);
	
	Client getClient(String id, ClientOptions options);

	URI createClient(Client client);
	
	Client updateClient(Client client, boolean fullState);

	Client updateClient(Client client);

	boolean deleteClient(String clientId);
	
	Page<Client> listClients(ClientFilter filter, Pageable options);
	
	void onClientUpdate(String id, Map<String, Object> details);

	void clearCache();
	
	Cache getClientCache();

	void onEvent(ApplicationEvent event);

	void evictCaches(String clientId);

}
