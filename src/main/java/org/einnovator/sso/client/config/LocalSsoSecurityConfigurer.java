package org.einnovator.sso.client.config;


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
import org.einnovator.sso.client.web.SsoCorsFilter;
import org.einnovator.sso.client.web.SsoQueryRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
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
@Profile("sso_local")
public class LocalSsoSecurityConfigurer {


	@Value("${spring.cache.ehcache.config:ehcache-sso-starter.xml}")
	private String cacheConfigLocation;

	@SuppressWarnings("unused")
	@Autowired
	private SsoClientConfiguration config;


	@Configuration
	public static class SsoClientFormSecurityConfigurer extends LocalFormSecurityConfigurer {
		
		@Autowired
		public SsoClientFormSecurityConfigurer(SsoClientConfiguration config, AuthenticationManager authenticationManager) {
			super(config, authenticationManager);
		}
	}

	//@Bean
	public InvitationManager invitationManager() {
		return new InvitationManagerImpl(ssoCacheManager());
	}

	//@Bean
	public GroupManager groupManager() {
		return new GroupManagerImpl(ssoCacheManager());
	}
	
	//@Bean
	public UserManager userManager() {
		return new UserManagerImpl(ssoCacheManager());
	}
	
	//@Bean
	public RoleManager roleManager() {
		return new RoleManagerImpl();
	}
	
	//@Bean
	public SsoManager ssoManager(OAuth2ClientContext context) {
		return new SsoManagerImpl();
	}

	//@Bean
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
	
}
