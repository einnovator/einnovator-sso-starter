/**
 * 
 */
package org.einnovator.sso.client.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.sso.client.SsoClient;
import org.einnovator.util.security.ClientTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
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
			OAuth2AccessToken token = ssoClient.setupClientToken();			
			return token;
		} catch (RuntimeException e) {
			logger.error(String.format("setupToken: %s", e));
			return null;
		}
	}

	@Override
	public OAuth2RestTemplate makeOAuth2RestTemplate() {
		return ssoClient.makeClientOAuth2RestTemplate();
	}

}
