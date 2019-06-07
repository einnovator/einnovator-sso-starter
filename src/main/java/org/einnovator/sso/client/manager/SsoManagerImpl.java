/**
 * 
 */
package org.einnovator.sso.client.manager;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.config.SsoClientConfiguration;
import org.einnovator.sso.client.model.SsoRegistration;
import org.einnovator.util.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

/**
 *
 */
public class SsoManagerImpl implements SsoManager {

	@Autowired
	private SsoClientConfiguration config;

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private SsoClient client;

	public SsoManagerImpl() {
	}

	@PostConstruct
	public void initialize() {
		if (config.getRegistration()!=null) {
			if (config.getRegistration().isAuto()) {
				register();
			}
		}
	}
	
	@Override
	public boolean register() {
		try {
			client.register();			
			return true;
		} catch (RuntimeException e) {
			logger.error("register: " + e);
			return false;
		}		
	}

	@Override
	public boolean register(Application application) {
		try {
			client.register(application);			
			return true;
		} catch (RuntimeException e) {
			logger.error("register: " + e + " " + application);
			return false;
		}
	}

	@Override
	public boolean register(Application application, OAuth2RestTemplate template) {
		try {
			client.register(application, template);			
			return true;
		} catch (RuntimeException e) {
			logger.error("register: " + e + " " + application);
			return false;
		}
	}

	@Override
	public boolean register(SsoRegistration registration) {
		try {
			client.register(registration);			
			return true;
		} catch (RuntimeException e) {
			logger.error("register: " + e + " " + registration);
			return false;
		}
	}
	
	@Override
	public boolean register(SsoRegistration registration, OAuth2RestTemplate template) {
		try {
			client.register(registration, template);
			return true;
		} catch (RuntimeException e) {
			logger.error("register: Attempt to register application failed: " + e + " " + registration);
			return false;
		}
	}
	
	


}
