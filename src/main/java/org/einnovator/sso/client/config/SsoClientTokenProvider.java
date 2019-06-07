/**
 * 
 */
package org.einnovator.sso.client.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.sso.client.SsoClient;
import org.einnovator.util.security.ClientTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 *
 */
public class SsoClientTokenProvider implements ClientTokenProvider {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private SsoClient ssoClient;

	@Override
	public OAuth2AccessToken setupToken() {
		try {
			return ssoClient.setupClientToken();			
		} catch (RuntimeException e) {
			logger.error(String.format("setupToken: %s", e));
			return null;
		}
	}

}
