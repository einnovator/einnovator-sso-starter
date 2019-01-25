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
			
			logger.info("logout: Invalidating session: " + session.getId());
			session.invalidate();
		}
		SecurityContext context = SecurityContextHolder.getContext();
	    logger.info("logout:" + principal.getName());
		if (context!=null) {
			logoutHandler.doLogout(request, response, context.getAuthentication());			
			context.setAuthentication(null);
			SecurityContextHolder.clearContext();
		}

	    String redirectUrl = request.getHeader("Referer");
	    logger.info("logout:" + redirectUrl);
	    if (SsoEndpoints.getLogoutEndpoint(config).equals(redirectUrl)) {
		    logger.info("logout:[COMPLETED]");
	    	return redirect("/");
	    } else {
		    logger.info("logout:[REDIRECT]: " + SsoEndpoints.getLogoutEndpoint(config));
			return "redirect:" + SsoEndpoints.getLogoutEndpoint(config);	    	
	    }
	}
	

	public static String redirect(String uri) {
		return "redirect:" + uri;	
	}
	

}
