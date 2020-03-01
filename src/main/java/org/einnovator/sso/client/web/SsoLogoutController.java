package org.einnovator.sso.client.web;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.sso.client.config.SsoClientConfiguration;
import org.einnovator.sso.client.config.SsoEndpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Logout filter used to invalidate session and tokens with SSO Gateway.
 *
 * @author support@einnovator.org
 *
 */
@Controller
public class SsoLogoutController {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private SsoClientConfiguration config;
	
	@Autowired
	private SsoClientLogoutHandler logoutHandler;
	
	public SsoLogoutController(SsoClientConfiguration config) {
		this.config = config;
	}
	
	public SsoLogoutController() {
	}
	
	@GetMapping({"/logout", "/_logout"})
	public String logout(HttpServletRequest request, HttpServletResponse response, Principal principal) {
		
		if (principal==null) {
			return redirect("/");
		}
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			if (logger.isDebugEnabled()) {
				logger.info(String.format("logout: Invalidating session: %s", session.getId()));				
			}
			session.invalidate();
		}
		SecurityContext context = SecurityContextHolder.getContext();
		if (logger.isDebugEnabled()) {
			logger.info(String.format("logout: %s", principal.getName()));				
		}
		if (context!=null) {
			logoutHandler.doLogout(request, response, context.getAuthentication());			
			context.setAuthentication(null);
			SecurityContextHolder.clearContext();
		}

	    String redirectUrl = request.getHeader("Referer");
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("logout: redirect: %s", redirectUrl));				
		}
	    if (SsoEndpoints.getLogoutEndpoint(config).equals(redirectUrl)) {
			if (logger.isDebugEnabled()) {
			    logger.debug("logout:[COMPLETED]");				
			}
	    	return redirect("/");
	    } else {
			if (logger.isDebugEnabled()) {
		    	logger.info(String.format("logout:[REDIRECT]: %s", SsoEndpoints.getLogoutEndpoint(config)));				
			}
			return redirect(SsoEndpoints.getLogoutEndpoint(config));	    	
	    }
	}
	

	public static String redirect(String uri) {
		return "redirect:" + uri;	
	}
	

}
