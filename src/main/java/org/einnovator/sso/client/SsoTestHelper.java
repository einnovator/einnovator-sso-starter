package org.einnovator.sso.client;

import org.einnovator.sso.client.config.SsoClientConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * Helper class for running tests that need a JWT bearer token to invoke secure services.
 * 
 * Example usage:<br>
 * <pre>
 * &#064;RunWith(SpringRunner.class)
 * &#064;SpringBootTest(classes = { MyAppConfig.class, MyAppTests.TestConfig.class }, webEnvironment = WebEnvironment.NONE)
 * &#064;TestPropertySource(properties = { "sso.server=http://sso-dev.mydomain.com", "spring.cache.ehcache.config:ehcache-sso-starter.xml" })
 * public class DocumentsClientTests extends SsoTestHelper {
 * 
 *   public static final String TEST_USER = "tdd@mydomain.com";
 *   public static final String TEST_PASSWORD = "may!pass";
 * 		
 *   private static final String CLIENT_ID = "myapp";
 *   private static final String CLIENT_SECRET = "myapp$secret";
 *   &#064;Autowired private SsoClient ssoClient;
 *   &#064;Configuration
 *   static class TestConfig extends SsoTestHelper.TestConfig {
 *   public TestConfig(ApplicationContext context) {
 *   	super(TEST_USER, TEST_PASSWORD, CLIENT_ID, CLIENT_SECRET, context);
 *   }
 *   &#064;Test
 *   public void mytest() {
 * 		OAuth2AccessToken token = client.getToken(TEST_USER, TEST_PASSWORD);
 * 		assertNotNull(token);
 *    	//test something with bearer token in SecurityContext
 *   	//...
 *   }
 * }
 * </pre>
 * @author support@einnovator.org
 *
 */
public class SsoTestHelper {


	@Autowired
	protected OAuth2ClientContext context;
	
	@Autowired
	protected SsoClient ssoClient;

	public SsoTestHelper() {
	}

	public static class TestConfig {

		public static final String DEFAULT_SERVER = "http://localhost:2000/auth";

		protected ApplicationContext context;
		 
		private String username;
		private String password;
		private String clientId;
		private String clientSecret;
		private String server;
		 
		public TestConfig(String username, String password, String clientId, String clientSecret, ApplicationContext context) {
			this.context = context;
			this.server = context.getEnvironment().getProperty("sso.server", DEFAULT_SERVER);
			this.username = username;
			this.password = password;
			this.clientId = clientId;
			this.clientSecret = clientSecret;
		}
		
		
		@Bean
		public SsoClient ssoClient() {
			SsoClientConfiguration config = new SsoClientConfiguration();
			config.setServer(server);
			config.setClientId(clientId);
			config.setClientSecret(clientSecret);
			return new SsoClient(config);
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
			cacheManagerFactoryBean.setConfigLocation(new ClassPathResource(context.getEnvironment().getProperty("spring.cache.ehcache.config", "ehcache.xml")));
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
