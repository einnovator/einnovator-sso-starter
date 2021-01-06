package org.einnovator.sso.client.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

public class LocalFormSecurityConfigurer  extends WebSecurityConfigurerAdapter {

	private SsoClientConfiguration config;
	private AuthenticationManager authenticationManager;

	public LocalFormSecurityConfigurer(SsoClientConfiguration config, AuthenticationManager authenticationManager) {
		this.config = config;
		this.authenticationManager = authenticationManager;
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(config.getIgnore()).permitAll()
			.antMatchers(config.getIgnoreInclude()).permitAll()
			//.requestMatchers(new FormSecurityConfigurer.FormRequestMatcher()).authenticated()
			.anyRequest().authenticated()
			.and().formLogin().permitAll().loginPage("/login")
			.and().logout().logoutUrl("/logout").permitAll()
			.and().addFilterBefore(new BasicAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
		
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
	


}
