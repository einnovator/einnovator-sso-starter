package org.einnovator.sso.client.config;


import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.manager.GroupManager;
import org.einnovator.sso.client.manager.GroupManagerImpl;
import org.einnovator.sso.client.manager.InvitationManager;
import org.einnovator.sso.client.manager.InvitationManagerImpl;
import org.einnovator.sso.client.manager.RoleManager;
import org.einnovator.sso.client.manager.RoleManagerImpl;
import org.einnovator.sso.client.manager.SsoManager;
import org.einnovator.sso.client.manager.SsoManagerImpl;
import org.einnovator.sso.client.manager.UserManager;
import org.einnovator.sso.client.manager.UserManagerImpl;
import org.einnovator.sso.client.web.SsoClientLogoutHandler;
import org.einnovator.sso.client.web.SsoCorsFilter;
import org.einnovator.sso.client.web.SsoLogoutController;
import org.einnovator.sso.client.web.SsoQueryRestController;
import org.einnovator.util.security.ClientTokenProvider;
import org.einnovator.util.security.UserTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.cors.CorsConfigurationSource;



/**
 * Spring Security Configurer for SSO Client.
 * 
 * @author support@einnovator.org
 *
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(value=SsoClientConfiguration.class)
@EnableCaching
@Import({OAuth2ResourcesConfigurer.class})
@Profile("!sso_exclude")
public class SsoClientSecurityConfigurer {


	@Value("${spring.cache.ehcache.config:ehcache-sso-starter.xml}")
	private String cacheConfigLocation;

	@Autowired
	private SsoClientConfiguration config;
	
	@Autowired
	private OAuth2ClientContext oauth2ClientContext;

	@Bean
	public SsoClientLogoutHandler logoutHandler() {
		return new SsoClientLogoutHandler(config);
	}

	
	@Configuration
	@Order(SecurityProperties.BASIC_AUTH_ORDER)
	public static class SsoClientBasicSecurityConfigurer extends BasicSecurityConfigurer {
	}

	@Configuration
	@EnableOAuth2Client
	public static class SsoClientFormSecurityConfigurer extends FormSecurityConfigurer {
		
		@Autowired
		public SsoClientFormSecurityConfigurer(SsoClientConfiguration config, SsoClientLogoutHandler logoutHandler, OAuth2ClientAuthenticationProcessingFilter filter) {
			super(config, logoutHandler, filter);
		}
	}
	
	
	@Bean
	public OAuth2RestTemplate ssoOAuth2RestTemplate() {
		OAuth2RestTemplate template = new OAuth2RestTemplate(ssoAuthProvider(), oauth2ClientContext);			
		template.setRequestFactory(config.getConnection().makeClientHttpRequestFactory());
		return template;
	}

	
	@Bean
	public OAuth2ClientAuthenticationProcessingFilter ssoFilter() {
		OAuth2RestTemplate template = ssoOAuth2RestTemplate();
		OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter("/login");
		filter.setRestTemplate(template);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(userinfoResource().getUserInfoUri(), ssoAuthProvider().getClientId());
		tokenServices.setRestTemplate(template);
		filter.setTokenServices(tokenServices);
		return filter;
	}

	@Bean
	@Primary
	public AuthorizationCodeResourceDetails ssoAuthProvider() {
		AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
		resource.setClientId(config.getClientId());
		resource.setClientSecret(config.getClientSecret());
		//redirect to SSO gateway login page, rather than /oauth/authorize directly
		//resource.setUserAuthorizationUri(SsoEndpoints.getLoginEndpoint(config));
		resource.setUserAuthorizationUri(SsoEndpoints.getAuthorizationEndpoint(config));
		resource.setAccessTokenUri(SsoEndpoints.getTokenEndpoint(config));
		resource.setTokenName(SsoClientConfiguration.OAUTH_TOKEN_NAME);
		resource.setAuthenticationScheme(AuthenticationScheme.header);
		resource.setClientAuthenticationScheme(AuthenticationScheme.header);
		resource.setScope(config.getScopes());
		return resource;
	}

	
	@Bean
	public ResourceServerProperties userinfoResource() {
		ResourceServerProperties resource = new ResourceServerProperties();
		resource.setUserInfoUri(SsoEndpoints.getUserInfoEndpoint(config));
		resource.setPreferTokenInfo(false);
		resource.setId("openid");
		return resource;
	}
	
	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}

	@Bean
	public InvitationManager invitationManager() {
		return new InvitationManagerImpl(ssoCacheManager());
	}

	@Bean
	public SsoClient ssoClient() {
		return new SsoClient(config);
	}
	
	@Bean
	public GroupManager groupManager() {
		return new GroupManagerImpl(ssoCacheManager());
	}
	
	@Bean
	public UserManager userManager() {
		return new UserManagerImpl(ssoCacheManager());
	}
	
	@Bean
	public RoleManager roleManager() {
		return new RoleManagerImpl();
	}
	
	@Bean
	public SsoManager ssoManager(OAuth2ClientContext context) {
		return new SsoManagerImpl();
	}

	@Bean
	public ClientTokenProvider clientTokenProvider() {
		return new SsoClientTokenProvider();
	}

	@Bean
	public UserTokenProvider userTokenProvider() {
		return new SsoUserTokenProvider();
	}

	@Bean
	public SsoLogoutController logoutController() {
		return new SsoLogoutController(config);
	}

	@Bean
	@ConditionalOnMissingBean(SsoQueryRestController.class)
	public SsoQueryRestController ssoQueryRestController() {
		return new SsoQueryRestController();
	}

	@Bean
	@Primary
	public CacheManager ssoCacheManager() {
		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		cacheManagerFactoryBean.setConfigLocation(new ClassPathResource(cacheConfigLocation));
		cacheManagerFactoryBean.setShared(true);
		cacheManagerFactoryBean.afterPropertiesSet();
		return new EhCacheCacheManager(cacheManagerFactoryBean.getObject());
	}
	
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SsoCorsFilter corsFilter(CorsConfigurationSource config) {
		return new SsoCorsFilter(config);
	}

	@Bean
	public ClientCredentialsResourceDetails clientResourceDetails() {
		return SsoClient.makeClientCredentialsResourceDetails(config);
	}

	
}
