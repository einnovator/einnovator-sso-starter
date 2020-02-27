package org.einnovator.sso.client.manager;


import java.net.URI;
import java.util.Map;

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.config.SsoClientContext;
import org.einnovator.sso.client.model.Client;
import org.einnovator.sso.client.modelx.ClientFilter;
import org.einnovator.sso.client.modelx.ClientOptions;
import org.einnovator.util.web.RequestOptions;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * High-level API for {@code Client}s remote operations.
 * 
 * @see Client
 * @see SsoClient
 * @author support@einnovator.org
 *
 */
public interface ClientManager {
	
	/**
	 * Get {@code Client} with specified identifier.
	 * 
	 * <p><b>Required Security Credentials</b>: Admin (global role ADMIN).
	 * 
	 * @param id the identifier (UUID)
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Client}, or null if not found or request fails
	 */
	Client getClient(String id, SsoClientContext context);
	
	/**
	 * Get {@code Client} with specified identifier.
	 * 
	 * <p><b>Required Security Credentials</b>: Admin (global role ADMIN).
	 * 
	 * @param id the identifier (UUID)
	 * @param options {@code ClientOptions} options (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Client}, or null if not found or request fails
	 */
	Client getClient(String id, ClientOptions options, SsoClientContext context);

	
	/**
	 * List {@code Client}s.
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param filter a {@code ClientFilter} (optional)
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Client}s, or null id request fails
	 */
	Page<Client> listClients(ClientFilter filter, Pageable pageable, SsoClientContext context);

	/**
	 * Create a new {@code Client}
	 * 
	 * <p><b>Required Security Credentials</b>: Admin (global role ADMIN).
	 * 
	 * @param client the {@code Client}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code Client}, or null if request failed
	 */
	URI createClient(Client client, RequestOptions options, SsoClientContext context);
	
	/**
	 * Update existing {@code Client}
	 * 
	 * <p><b>Required Security Credentials</b>: Admin (global role ADMIN).
	 * 
	 * @param client the {@code Client}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the same {@code Client}, or null if request failed
	 */
	Client updateClient(Client client, RequestOptions options, SsoClientContext context);

	/**
	 * Delete existing {@code Client}
	 * 
	 * <p><b>Required Security Credentials</b>: Admin (global role ADMIN).
	 * 
	 * @param clientId the {@code Client}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return true if {@code Client} was deleted, or false if request failed
	 */
	boolean deleteClient(String clientId, RequestOptions options, SsoClientContext context);
	
	//
	// Caching
	//
	
	/**
	 * Update cache entry for {@code Client}.
	 * 
	 * @param id the {@code Client} UUID
	 * @param details new state of {@code User}
	 * @param context optional {@code SsoClientContext}
	 */
	void onClientUpdate(String id, Map<String, Object> details, SsoClientContext context);

	/**
	 * Clear the cache for {@code Client}s.
	 * 
	 */
	void clearCache();
	
	/**
	 * Get the cache for {@code Client}s.
	 * 
	 * @return the {@code Cache}
	 */
	Cache getClientCache();

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
	 * @param clientId the clientId
	 */
	void evictCaches(String clientId);

}
