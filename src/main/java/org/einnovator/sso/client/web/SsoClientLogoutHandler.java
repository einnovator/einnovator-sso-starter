package org.einnovator.sso.client.web;

import static org.einnovator.util.UriUtils.makeURI;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.sso.client.config.SsoClientConfiguration;
import org.einnovator.sso.client.config.SsoEndpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.client.RestTemplate;

/**
 * A logout handler.
 *
 * @author support@einnovator.org
 *
 */
public class SsoClientLogoutHandler extends SimpleUrlLogoutSuccessHandler implements LogoutHandler, LogoutSuccessHandler {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private SsoClientConfiguration config;
	
	private RestTemplate template;

	public SsoClientLogoutHandler(SsoClientConfiguration config) {
		this.template = new RestTemplate();
		this.config = config;
	}
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		doLogout(request, response, authentication);
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		doLogout(request, response, authentication);
		super.onLogoutSuccess(request, response, authentication);
	}
	
	protected void doLogout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		logger.info("logout:" + authentication);
		if (authentication==null) {
			return;
		}
		Object details = authentication.getDetails();
		if (details.getClass().isAssignableFrom(OAuth2AuthenticationDetails.class)) {
			String accessToken = ((OAuth2AuthenticationDetails)details).getTokenValue();
			@SuppressWarnings("rawtypes")
			RequestEntity request2 = RequestEntity.post(makeURI(SsoEndpoints.getTokenRevokeEndpoint(config)))
					.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", "Bearer " + accessToken).build();
			try {
				ResponseEntity<Void> response2 = template.exchange(request2, Void.class);
				logger.info("logout:" + response2);				
			} catch (RuntimeException e) {
				logger.error("logout:" + e);				
			}
		}
	}
}
