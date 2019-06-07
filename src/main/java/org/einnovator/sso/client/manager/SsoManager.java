/**
 * 
 */
package org.einnovator.sso.client.manager;

import org.einnovator.sso.client.model.SsoRegistration;
import org.einnovator.util.model.Application;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

/**
 *
 */
public interface SsoManager {
	boolean register();
	boolean register(Application application);
	boolean register(Application application, OAuth2RestTemplate restTemplate);
	boolean register(SsoRegistration registration);
	boolean register(SsoRegistration registration, OAuth2RestTemplate restTemplate);

}
