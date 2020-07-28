package org.einnovator.sso.client.config;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

public class LocalFormSecurityConfigurer  extends WebSecurityConfigurerAdapter {

	private SsoClientConfiguration config;
	
	public LocalFormSecurityConfigurer(SsoClientConfiguration config) {
		this.config = config;
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(config.getIgnore()).permitAll()
			.antMatchers(config.getIgnoreInclude()).permitAll()
			//.requestMatchers(new FormRequestMatcher()).authenticated()
			.anyRequest().authenticated()
			.and().formLogin().permitAll().loginPage("/login")
			.and().logout().logoutUrl("/logout").permitAll();
		
			if (Boolean.TRUE.equals(config.getCsrfEnabled())) {
				http.csrf().ignoringAntMatchers(config.getCsrfIgnore());
			} else {
				http.csrf().disable();
			}
			http.headers().frameOptions().disable();
			if (config.getSessionCreationPolicy()!=null) {
				http.sessionManagement().sessionCreationPolicy(config.getSessionCreationPolicy());				
			}
	}

	@Bean
	@Primary
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
		// setAllowCredentials(true) is important, otherwise: 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
		configuration.setAllowCredentials(true);
		// setAllowedHeaders is important! Without it, OPTIONS preflight request will fail with 403 Invalid CORS request
		//configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "Location", "Content-Length"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	public static class FormRequestMatcher implements RequestMatcher {
		
		private AuthorizationTokenExtractor tokenExtractor = new AuthorizationTokenExtractor();
		
		@Override
		public boolean matches(HttpServletRequest request)  {
			Authentication authentication = tokenExtractor.extract(request);
			return authentication!=null;
		}
	}


	public static class AuthorizationTokenExtractor implements TokenExtractor {

		@Override
		public Authentication extract(HttpServletRequest request) {
			String tokenValue = extractToken(request);
			if (tokenValue != null) {
				PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(tokenValue, "");
				return authentication;
			}
			return null;
		}

		protected String extractToken(HttpServletRequest request) {
			String token = extractHeaderToken(request);
			return token;
		}

		
		/**
		 * Extract the Basic token from a header.
		 * 
		 * @param request The request.
		 * @return The token, or null if no BASIC authorization header was supplied.
		 */
		protected String extractHeaderToken(HttpServletRequest request) {
			Enumeration<String> headers = request.getHeaders(HttpHeaders.AUTHORIZATION);
			while (headers.hasMoreElements()) { // typically there is only one (most servers enforce that)
				String value = headers.nextElement();
				if (StringUtils.hasText(value)) {
					return value.trim();
				}
			}
			return null;
		}

	}


}
