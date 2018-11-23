package org.einnovator.sso.client;


import org.einnovator.sso.client.manager.GroupManager;
import org.einnovator.sso.client.manager.GroupManagerImpl;
import org.einnovator.sso.client.manager.InvitationManager;
import org.einnovator.sso.client.manager.InvitationManagerImpl;
import org.einnovator.sso.client.manager.PermissionManager;
import org.einnovator.sso.client.manager.PermissionManagerImpl;
import org.einnovator.sso.client.manager.RoleManager;
import org.einnovator.sso.client.manager.RoleManagerImpl;
import org.einnovator.sso.client.manager.SsoCacheResolver;
import org.einnovator.sso.client.manager.UserManager;
import org.einnovator.sso.client.manager.UserManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(value=SsoClientConfiguration.class)
@EnableCaching
@Import(OAuth2ResourcesConfigurer.class)
public class SsoClientSecurityConfigurer {


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

	@Bean(name="ssoClientHttpRequestFactory")
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		ConnectionConfiguration connectionConf = config.getConnection();
		return connectionConf.makeClientHttpRequestFactory();
	}
	
	
	
	@Bean
	public OAuth2RestTemplate ssoOAuth2RestTemplate() {
		OAuth2RestTemplate template = new OAuth2RestTemplate(ssoAuthProvider(), oauth2ClientContext);			
		template.setRequestFactory(clientHttpRequestFactory());
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
	public PermissionManager permissionManager() {
		return new PermissionManagerImpl();
	}

	@Bean
	public InvitationManager invitationManager() {
		return new InvitationManagerImpl(ssoCacheManager());
	}
	
	@Bean
	public SsoLogoutController logoutController() {
		return new SsoLogoutController(config);
	}

	@Bean
	@Primary
	public CacheManager ssoCacheManager() {
		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cacheManagerFactoryBean.setShared(true);
		cacheManagerFactoryBean.afterPropertiesSet();
		return new EhCacheCacheManager(cacheManagerFactoryBean.getObject());
	}
	
	@Bean
	public SsoCacheResolver ssoCacheResolver() {
		return new SsoCacheResolver(ssoCacheManager());
	}
	
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SsoCorsFilter corsFilter(CorsConfigurationSource config) {
		return new SsoCorsFilter(config);
	}

}
