/**
 * 
 */
package org.einnovator.sso.client.config;

import org.einnovator.util.web.ClientContext;

/**
 *
 */
public class SsoClientContext extends ClientContext {
	
	private SsoClientConfiguration config;
	
	/**
	 * Create instance of {@code SsoContext}.
	 *
	 */
	public SsoClientContext() {
	}

	/**
	 * Get the value of property {@code config}.
	 *
	 * @return the config
	 */
	public SsoClientConfiguration getConfig() {
		return config;
	}

	/**
	 * Set the value of property {@code config}.
	 *
	 * @param config the value of property config
	 */
	public void setConfig(SsoClientConfiguration config) {
		this.config = config;
	}
	
}
