package org.einnovator.sso.client.support;

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.config.SsoClientConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class SsoTestHelper {


	@Autowired
	protected OAuth2ClientContext context;
	
	@Autowired
	protected SsoClient ssoClient;

	public SsoTestHelper() {
	}

	public static class TestConfig {

		public static final String DEFAULT_SERVER = "http://localhost:28081/auth";
		private String username;
		private String password;
		private String clientId;
		private String clientSecret;
		private String server;
		 
		public TestConfig(String server, String username, String password, String clientId, String clientSecret) {
			this.server = server;
			this.username = username;
			this.password = password;
			this.clientId = clientId;
			this.clientSecret = clientSecret;
		}
		
		public TestConfig(String username, String password, String clientId, String clientSecret) {
			this(DEFAULT_SERVER, username, password, clientId, clientSecret);
		}
		
		@Bean
		public SsoClient ssoClient() {
			SsoClientConfiguration config = new SsoClientConfiguration();
			config.setServer(server);
			config.setClientId(clientId);
			config.setClientSecret(clientSecret);
			return new SsoClient(config, config.getConnection().makeClientHttpRequestFactory());
		}
		
		@Bean
		public OAuth2ClientContext context() {
			DefaultOAuth2ClientContext context = new DefaultOAuth2ClientContext();
			OAuth2AccessToken accessToken = ssoClient().getToken(username, password, context);	
			context.setAccessToken(accessToken);
			return context;
		}
		
		@Bean
		public OAuth2ProtectedResourceDetails resourceDetails() {
			return ssoClient().makeResourceOwnerPasswordResourceDetails(username, password);
		}
		

		@Bean
		public OAuth2RestTemplate ssoOAuth2RestTemplate() {
			OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails(), context());			
			return template;
		}

		@Bean
		public CacheManager cacheManager() {
			EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
			cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
			cacheManagerFactoryBean.setShared(true);
			cacheManagerFactoryBean.afterPropertiesSet();
			return new EhCacheCacheManager(cacheManagerFactoryBean.getObject());
		}
	}
	

	protected void setPrincipal(String username, String password) {
		context.setAccessToken(null);
		OAuth2AccessToken accessToken = ssoClient.getToken(username, password, context);	
		context.setAccessToken(accessToken);
	}
	
}
