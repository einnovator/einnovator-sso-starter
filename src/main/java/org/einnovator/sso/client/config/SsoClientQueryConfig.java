package org.einnovator.sso.client.config;


import org.einnovator.sso.client.web.SsoQueryRestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class SsoClientQueryConfig {

	@Bean
	public SsoQueryRestController queryController() {
		return new SsoQueryRestController();
	}
}
