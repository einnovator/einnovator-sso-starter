package org.einnovator.sso.client;

import static org.einnovator.util.UriUtils.appendQueryParameters;
import static org.einnovator.util.UriUtils.encode;
import static org.einnovator.util.UriUtils.encodeId;
import static org.einnovator.util.UriUtils.makeURI;

import java.net.URI;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.sso.client.config.SsoClientConfiguration;
import org.einnovator.sso.client.config.SsoClientContext;
import org.einnovator.sso.client.config.SsoEndpoints;
import org.einnovator.sso.client.model.Client;
import org.einnovator.sso.client.model.Group;
import org.einnovator.sso.client.model.GroupType;
import org.einnovator.sso.client.model.Invitation;
import org.einnovator.sso.client.model.InvitationStats;
import org.einnovator.sso.client.model.Member;
import org.einnovator.sso.client.model.Role;
import org.einnovator.sso.client.model.SsoRegistration;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.ClientFilter;
import org.einnovator.sso.client.modelx.ClientOptions;
import org.einnovator.sso.client.modelx.GroupFilter;
import org.einnovator.sso.client.modelx.InvitationFilter;
import org.einnovator.sso.client.modelx.InvitationOptions;
import org.einnovator.sso.client.modelx.MemberFilter;
import org.einnovator.sso.client.modelx.RoleFilter;
import org.einnovator.sso.client.modelx.UserFilter;
import org.einnovator.sso.client.modelx.UserOptions;
import org.einnovator.util.MappingUtils;
import org.einnovator.util.PageOptions;
import org.einnovator.util.PageResult;
import org.einnovator.util.PageUtil;
import org.einnovator.util.model.Application;
import org.einnovator.util.security.SecurityUtil;
import org.einnovator.util.web.Result;
import org.einnovator.util.web.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class SsoClient {

	private final Log logger = LogFactory.getLog(getClass());

	private SsoClientConfiguration config;

	@Autowired
	@Qualifier("ssoOAuth2RestTemplate")
	private OAuth2RestTemplate restTemplate;

	@Autowired
	private OAuth2ClientContext oauth2ClientContext;

	private OAuth2ClientContext oauth2ClientContext0 = new DefaultOAuth2ClientContext();

	private OAuth2RestTemplate restTemplate0;

	private ClientHttpRequestFactory clientHttpRequestFactory;

	private boolean autoSetupToken;
	
	@Autowired(required=false)
	private Application application;

	@Autowired
	public SsoClient(SsoClientConfiguration config) {
		this.config = config;
	}

	public SsoClient(OAuth2RestTemplate restTemplate, SsoClientConfiguration config) {
		this.config = config;
		this.restTemplate = restTemplate;
		this.oauth2ClientContext = restTemplate.getOAuth2ClientContext();
	}

	public SsoClient(SsoClientConfiguration config, ClientHttpRequestFactory clientHttpRequestFactory) {
		this.config = config;
		this.clientHttpRequestFactory = clientHttpRequestFactory;
	}

	@PostConstruct
	public void init() {
		if (clientHttpRequestFactory==null) {
			clientHttpRequestFactory = config.getConnection().makeClientHttpRequestFactory();
		}
	}
	
	/**
	 * Get the value of property {@code application}.
	 *
	 * @return the application
	 */
	public Application getApplication() {
		return application;
	}

	/**
	 * Set the value of property {@code application}.
	 *
	 * @param application the application to set
	 */
	public void setApplication(Application application) {
		this.application = application;
	}

	/**
	 * Get the value of property {@code config}.
	 *
	 * @return the config
	 */
	public SsoClientConfiguration getConfig() {
		return config;
	}

	/**
	 * Set the value of property {@code config}.
	 *
	 * @param config the config to set
	 */
	public void setConfig(SsoClientConfiguration config) {
		this.config = config;
	}

	/**
	 * Get the value of property {@code restTemplate}.
	 *
	 * @return the restTemplate
	 */
	public OAuth2RestTemplate getRestTemplate() {
		return restTemplate;
	}

	/**
	 * Set the value of property {@code restTemplate}.
	 *
	 * @param restTemplate the restTemplate to set
	 */
	public void setRestTemplate(OAuth2RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * Get the value of property {@code clientHttpRequestFactory}.
	 *
	 * @return the clientHttpRequestFactory
	 */
	public ClientHttpRequestFactory getClientHttpRequestFactory() {
		return clientHttpRequestFactory;
	}

	/**
	 * Set the value of property {@code clientHttpRequestFactory}.
	 *
	 * @param clientHttpRequestFactory the clientHttpRequestFactory to set
	 */
	public void setClientHttpRequestFactory(ClientHttpRequestFactory clientHttpRequestFactory) {
		this.clientHttpRequestFactory = clientHttpRequestFactory;
	}

	/**
	 * Get the value of property {@code oauth2ClientContext}.
	 *
	 * @return the oauth2ClientContext
	 */
	public OAuth2ClientContext getOauth2ClientContext() {
		return oauth2ClientContext;
	}

	/**
	 * Set the value of property {@code oauth2ClientContext}.
	 *
	 * @param oauth2ClientContext the oauth2ClientContext to set
	 */
	public void setOauth2ClientContext(OAuth2ClientContext oauth2ClientContext) {
		this.oauth2ClientContext = oauth2ClientContext;
	}

	/**
	 * Get the value of property {@code oauth2ClientContext0}.
	 *
	 * @return the oauth2ClientContext0
	 */
	public OAuth2ClientContext getOauth2ClientContext0() {
		return oauth2ClientContext0;
	}

	/**
	 * Set the value of property {@code oauth2ClientContext0}.
	 *
	 * @param oauth2ClientContext0 the oauth2ClientContext0 to set
	 */
	public void setOauth2ClientContext0(OAuth2ClientContext oauth2ClientContext0) {
		this.oauth2ClientContext0 = oauth2ClientContext0;
	}

	/**
	 * Get the value of property {@code restTemplate0}.
	 *
	 * @return the restTemplate0
	 */
	public OAuth2RestTemplate getRestTemplate0() {
		return restTemplate0;
	}

	/**
	 * Set the value of property {@code restTemplate0}.
	 *
	 * @param restTemplate0 the restTemplate0 to set
	 */
	public void setRestTemplate0(OAuth2RestTemplate restTemplate0) {
		this.restTemplate0 = restTemplate0;
	}

	public boolean isAutoSetupToken() {
		return autoSetupToken;
	}


	public void setAutoSetupToken(boolean autoSetupToken) {
		this.autoSetupToken = autoSetupToken;
	}


	//
	// Registration
	//

	/**
	 * Register client application data with default server using default configured {@code SsoRegistration}.
	 *  
	 * @see SsoClientConfiguration
	 * @see SsoRegistration
	 */
	public void register() {
		SsoRegistration registration = config.getRegistration();
		if (registration!=null) {
			if (application!=null) {
				registration.setApplication(application);
			}
			try {
				register(registration);							
			} catch (RuntimeException e) {
				throw e;
			}
		}
	}

	/**
	 * Register client application data with default server.
	 *  
	 * @param registration the {@code SsoRegistration}
	 */
	public void register(SsoRegistration registration) {
		setupClientToken(oauth2ClientContext0);
		register(registration, makeClientOAuth2RestTemplate());
	}

	/**
	 * Register client application data.
	 * 
	 * <b>Required Security Credentials</b>: Client or Admin (global ROLE_ADMIN).
	 *  
	 * @param registration the {@code SsoRegistration}
	 * @param restTemplate the {@code OAuth2RestTemplate} used to connect to server
	 */
	public void register(SsoRegistration registration, OAuth2RestTemplate restTemplate) {
		URI uri = makeURI(SsoEndpoints.register(config));
		RequestEntity<SsoRegistration> request = RequestEntity.post(uri).body(registration);
		exchange(restTemplate, request, Void.class);
	}
	
	//
	// User
	//
	
	/**
	 * Get {@code User} with specified identifier.
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID, username, email.
	 * 
	 * <b>Required Security Credentials</b>: Any, but results depend on each {@code User} privacy settings.
	 * 
	 * @param id the {@code User} uuid or username
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 * @return the {@code User} if found, null otherwise.
	 */
	public User getUser(String id, SsoClientContext context) {
		return getUser(id, UserOptions.DEFAULT, context);
	}

	/**
	 * Get {@code User} with specified identifier.
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID, username, email.
	 * 
	 * <b>Required Security Credentials</b>: Any, but results depend on each {@code User} privacy settings.
	 * 
	 * @param id the identifier
	 * @param options (optional) the {@code UserOptions} that tailor which fields are returned (projection)
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code User}
	 * @throws RestClientException if request fails
	 */
	public User getUser(String id, UserOptions options, SsoClientContext context) {
		id = encodeId(id);
		URI uri = makeURI(SsoEndpoints.user(id, config));
		if (options!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			params.putAll(MappingUtils.toMapFormatted(options));
			uri = appendQueryParameters(uri, params);			
		}

		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<User> result = exchange(request, User.class, context);
		return result.getBody();
	}
	
	
	/**
	 * List {@code User}s.
	 * 
	 * <b>Required Security Credentials</b>: Any, but results depend on credentials and each {@code User} privacy settings.
	 * 
	 * @param filter a {@code UserFilter}
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 * @return a {@code Page} with {@code User}s
	 */
	public Page<User> listUsers(UserFilter filter, Pageable pageable, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.users(config));
		if (filter!=null || pageable!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			if (filter!=null) {
				params.putAll(MappingUtils.toMapFormatted(filter));
			}
			if (pageable!=null) {
				params.putAll(MappingUtils.toMapFormatted(new PageOptions(pageable)));
			}
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		@SuppressWarnings("rawtypes")
		ResponseEntity<PageResult> result = exchange(request, PageResult.class, context);
		return PageUtil.create2(result.getBody(),  User.class);
	}

	/**
	 * Create a new {@code User}
	 * 
	 * 
	 * <b>Required Security Credentials</b>: Client or Admin (global ROLE_ADMIN).
	 * 
	 * @param user the {@code User}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code User}
	 * @throws RestClientException if request fails
	 */
	public URI createUser(User user, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.users(config));
		RequestEntity<User> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(user);
		
		ResponseEntity<Void> result = exchange(request, Void.class, context);
		return result.getHeaders().getLocation();
	}
	
	/**
	 * Update existing {@code User}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), or owner.
	 * 
	 * @param user the {@code User}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void updateUser(User user, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.user(user.getId(), config));
		RequestEntity<User> request = RequestEntity.put(uri).accept(MediaType.APPLICATION_JSON).body(user);
		
		exchange(request, User.class, context);
	}
	
	/**
	 * Delete existing {@code User}
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID, username, email.
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), or owner.
	 * 
	 * @param id the {@code User} identifier
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void deleteUser(String id, SsoClientContext context) {
		id = encodeId(id);
		URI uri = makeURI(SsoEndpoints.user(id, config));
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		
		exchange(request, Void.class, context);
	}
	

	//
	// Password
	// 

	/**
	 * Change a {@code User} password.
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), owner {@code User}.
	 * 
	 * @param password the password
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void changePassword(String password, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.password(config) + "?password=" + password);
		RequestEntity<Void> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class, context);
	}

	
	//
	// Group
	//
	
	/**
	 * Create a new {@code Group}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), any for root {@code Group}s. 
	 * For sub-{@code Group}s: owner or <b>ROLE_GROUP_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param group the {@code Group}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code User}
	 * @throws RestClientException if request fails
	 */
	public URI createGroup(Group group, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.groups(config));
		RequestEntity<Group> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(group);
		
		ResponseEntity<Group> result = exchange(request, Group.class, context);
		return result.getHeaders().getLocation();
	}

	/**
	 * Get {@code Group} with specified identifier.
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID, name for root {@code Groups} if server is configured to required unique names for root {@code Group}s .
	 * 
	 * <b>Required Security Credentials</b>:  any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param id the identifier
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Group}
	 * @throws RestClientException if request fails
	 */
	public Group getGroup(String id, SsoClientContext context) {
		return getGroup(id, null, context);
	}

	/**
	 * Get {@code Group} with specified identifier.
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID, name for root {@code Groups} if server is configured to required unique names for root {@code Group}s .
	 * 
	 * <b>Required Security Credentials</b>:  any for root {@code Group}, but results depend on each {@code User} privacy settings.
	 *
	 * @param id the identifier
	 * @param filter (optional) the {@code GroupOptions} that tailor which fields are returned (projection) and {@code GroupFilter} for sub-groups
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Group}
	 * @throws RestClientException if request fails
	 */
	public Group getGroup(String id, GroupFilter filter, SsoClientContext context) {
		id = encode(id);
		URI uri = makeURI(SsoEndpoints.group(id, config));
		
		if (filter!=null) {
			uri = appendQueryParameters(uri, filter);
		}

		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Group> result = exchange(request, Group.class, context);
		return result.getBody();
	}
	
	/**
	 * Update existing {@code Group}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN).
	 * For root {@code Group}s: owner or <b>ROLE_GROUP_MANAGER</b> in {@code Group}
	 * For sub-{@code Group}s: owner or <b>ROLE_GROUP_MANAGER</b> in {@code Group}, owner or <b>ROLE_GROUP_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param group the {@code Group}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void updateGroup(Group group, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.group(encode(group.getId()), config));
		RequestEntity<Group> request = RequestEntity.put(uri).accept(MediaType.APPLICATION_JSON).body(group);		
		exchange(request, Group.class, context);
	}
	
	/**
	 * List {@code Group}s.
	 * 
	 * <b>Required Security Credentials</b>:  any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param filter a {@code GroupFilter}
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Group}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Group> listGroups(GroupFilter filter, Pageable pageable, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.groups(config));
		if (filter!=null || pageable!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			if (filter!=null) {
				params.putAll(MappingUtils.toMapFormatted(filter));
			}
			if (pageable!=null) {
				params.putAll(MappingUtils.toMapFormatted(new PageOptions(pageable)));
			}
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		@SuppressWarnings("rawtypes")
		ResponseEntity<PageResult> result = exchange(request, PageResult.class, context);
		return PageUtil.create2(result.getBody(),  Group.class);
	}
	
	/**
	 * Delete existing {@code Group}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN).
	 * For root {@code Group}s: owner or <b>ROLE_GROUP_MANAGER</b> in {@code Group}
	 * For sub-{@code Group}s: owner or <b>ROLE_GROUP_MANAGER</b> in {@code Group}, owner or <b>ROLE_GROUP_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param id the {@code Group} identifier
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void deleteGroup(String id, SsoClientContext context) {
		id = encode(id);
		URI uri = makeURI(SsoEndpoints.group(id, config));
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class, context);
	}
	
	//
	// Group Tree
	//

	/**
	 * List sub-{@code Group}s.
	 * 
	 * <b>Required Security Credentials</b>:  any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param id the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param type a type of {@code Group} (optional)
	 * @param direct true if count only direct sub-group, false if count the all tree
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Group}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Group> listSubGroups(String id, GroupType type, boolean direct, Pageable pageable, SsoClientContext context) {
		id = encode(id);
		GroupFilter filter = new GroupFilter();
		if (direct) {
			filter.setParent(id);			
		} else {
			filter.setRoot(id);
		}
		filter.setType(type);
		return listGroups(filter, pageable, context);
	}

	/**
	 * Count {@code Group}s matching specified {@code GroupFilter}.
	 * 
	 * <b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param filter a {@code GroupFilter}
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Group}s
	 * @throws RestClientException if request fails
	 */	
	public Integer countGroups(GroupFilter filter, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.countGroups(config));
		if (filter!=null) {
			uri = appendQueryParameters(uri, filter);			
		}
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Integer> result = exchange(request, Integer.class, context);
		return result.getBody();
	}
	
	/**
	 * Count number of sub-{@code Group}s for specified {@code Group}.
	 * 
	 * <b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param id the {@code id} identifier (UUID, or name of root group if supported)
	 * @param type the type of sub-{@code Group} (optional)
	 * @param direct true if count only direct sub-group, false if count the all tree
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Group}s
	 * @throws RestClientException if request fails
	 */	
	public Integer countSubGroups(String id, GroupType type, boolean direct, SsoClientContext context) {
		GroupFilter filter = new GroupFilter();
		if (direct) {
			filter.setParent(id);			
		} else {
			filter.setRoot(id);
		}
		filter.setType(type);
		return countGroups(filter, context);
	}

	//
	// Group Membership
	//
	
	/**
	 * List {@code Member} of a {@code Group} .
	 * 
	 * <b>Required Security Credentials</b>:  any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings,
	 * and each {@code User}  privacy settings.
	 * 
	 * @param id the {@code id} identifier (UUID, or name of root group if supported)
	 * @param filter a {@code MemberFilter}
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Member}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Member> listGroupMembers(String id, MemberFilter filter, Pageable pageable, SsoClientContext context) {
		id = encode(id);
		URI uri = makeURI(SsoEndpoints.groupMembers(id, config));
		if (filter!=null || pageable!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			if (filter!=null) {
				params.putAll(MappingUtils.toMapFormatted(filter));
			}
			if (pageable!=null) {
				params.putAll(MappingUtils.toMapFormatted(new PageOptions(pageable)));
			}
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		@SuppressWarnings("rawtypes")
		ResponseEntity<PageResult> result = exchange(request, PageResult.class, context);
		return PageUtil.create2(result.getBody(),  Member.class);
	}

	/**
	 * Get count of {@code Member}  in a {@code Group} .
	 * 
	 * <b>Required Security Credentials</b>:  any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings,
	 * and each {@code User} privacy settings.
	 * 
	 * @param id the {@code id} identifier (UUID, or name of root group if supported)
	 * @param filter a {@code MemberFilter}
	 * @param context optional {@code SsoClientContext}
	 * @return the count of {@code Member} (users)
	 * @throws RestClientException if request fails
	 */	
	public Integer countGroupMembers(String id, MemberFilter filter, SsoClientContext context) {
		id = encode(id);
		URI uri = makeURI(SsoEndpoints.countMembers(id, config));
		if (filter!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			if (filter!=null) {
				params.putAll(MappingUtils.toMapFormatted(filter));
			}
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Integer> result = exchange(request, Integer.class, context);
		return result.getBody();
	}

	/**
	 * Get {@code Member} with specified identifier.
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID of {@code member}, of username od {@code User}
	 * 
	 * <b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings,
	 * and each {@code User} privacy settings.
	 * 
	 * Request is ignored if {@code User} is already member of {@code Group}.
	 *
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Group}
	 * @throws RestClientException if request fails
	 */
	public Member getGroupMember(String groupId, String userId, SsoClientContext context) {
		groupId = encode(groupId);
		userId = encodeId(userId);
		URI uri = makeURI(SsoEndpoints.member(groupId, userId, config));
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Member> result = exchange(request, Member.class, context);
		try {
			return result.getBody();			
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode()==HttpStatus.NOT_FOUND) {
				return null;
			}
			throw e;
		}
	}

	/**
	 * Add user a new {@code Group}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), any for root {@code Group}s. 
	 * For sub-{@code Group}s: owner or <b>ROLE_GROUP_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code Member}
	 * @throws RestClientException if request fails
	 */
	public URI addMemberToGroup(String userId, String groupId, SsoClientContext context) {
		groupId = encode(groupId);
		userId = encodeId(userId);
		URI uri = makeURI(SsoEndpoints.groupMembers(groupId, config) + "?username=" + userId);
		RequestEntity<Void> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).build();		
		ResponseEntity<Void> result = exchange(request, Void.class, context);
		return result.getHeaders().getLocation();
	}
	
	/**
	 * Add user a new {@code Group}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), any for root {@code Group}s. 
	 * For sub-{@code Group}s: owner or <b>ROLE_GROUP_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param member the {@code Member} to add to Group
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code Member}
	 * @throws RestClientException if request fails
	 */
	public URI addMemberToGroup(Member member, String groupId, SsoClientContext context) {
		groupId = encode(groupId);
		URI uri = makeURI(SsoEndpoints.groupMembers(groupId, config));
		if (member!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			if (member!=null) {
				params.putAll(MappingUtils.toMapFormatted(member));
			}
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Void> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).build();		
		ResponseEntity<Void> result = exchange(request, Void.class, context);
		return result.getHeaders().getLocation();
	}
	
	/**
	 * Remove {@code User} from a {@code Group}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), owner {@code User}.
	 * For root {@code Group}s: owner or <b>ROLE_GROUP_MANAGER</b> in {@code Group}
	 * For sub-{@code Group}s: owner or <b>ROLE_GROUP_MANAGER</b> in {@code Group}, owner or <b>ROLE_GROUP_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void removeMemberFromGroup(String userId, String groupId, SsoClientContext context) {
		groupId = encode(groupId);
		userId = encodeId(userId);
		URI uri = makeURI(SsoEndpoints.groupMembers(groupId, config) + "?username=" + userId);
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class, context);
	}
	
	/**
	 * List {@code Group}s a {@code User} is member.
	 * 
	 * <b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings,
	 * and each {@code User} privacy settings.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param type a type of {@code Group} (optional)
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Group}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Group> listGroupsForUser(String userId, GroupType type, Pageable pageable, SsoClientContext context) {
		GroupFilter filter = new GroupFilter();
		filter.setOwner(userId);
		filter.setType(type);
		return listGroups(filter, pageable, context);
	}

	//
	// Invitation
	//
	
	/**
	 * Create a new {@code Invitation}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), any. 
	 * 
	 * @param invitation the {@code Invitation}
	 * @param sendMail true is send email
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code Invitation}
	 * @throws RestClientException if request fails
	 */
	public URI invite(Invitation invitation, Boolean sendMail, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.invite(config));
		if (sendMail!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			if (sendMail!=null) {
				params.put("sendMail", sendMail!=null ? Boolean.toString(sendMail) : null);				
			}
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Invitation> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(invitation);		
		ResponseEntity<Void> result = exchange(request, Void.class, context);
		return result.getHeaders().getLocation();
	}
	
	/**
	 * List {@code Invitation}s.
	 * 
	 * <b>Required Security Credentials</b>:  any, but results depend user, and each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param filter a {@code InvitationFilter}
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Invitation}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Invitation> listInvitations(InvitationFilter filter, Pageable pageable, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.invitations(config));
		if (filter!=null || pageable!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			if (filter!=null) {
				params.putAll(MappingUtils.toMapFormatted(filter));
			}
			if (pageable!=null) {
				params.putAll(MappingUtils.toMapFormatted(new PageOptions(pageable)));
			}
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		@SuppressWarnings("rawtypes")
		ResponseEntity<PageResult> result = exchange(request, PageResult.class, context);
		return PageUtil.create2(result.getBody(),  Invitation.class);
	}

	/**
	 * Get {@code Invitation} with specified identifier.
	 * 
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), owner.
	 *
	 * @param id the identifier (UUID)
	 * @param options (optional) the {@code InvitationOptions} that tailor which fields are returned (projection)
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Invitation}
	 * @throws RestClientException if request fails
	 */
	public Invitation getInvitation(String id, InvitationOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.invitation(id, config));
		if (options!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			params.putAll(MappingUtils.toMapFormatted(new PageOptions(options)));
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Invitation> result = exchange(request, Invitation.class, context);
		return result.getBody();
	}

	/**
	 * Update existing {@code Invitation}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), or owner.
	 * 
	 * @param invitation the {@code Invitation}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void updateInvitation(Invitation invitation, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.invitation(invitation.getUuid(), config));
		RequestEntity<Invitation> request = RequestEntity.put(uri).accept(MediaType.APPLICATION_JSON).body(invitation);

		exchange(request, Invitation.class, context);
	}

	/**
	 * Update existing {@code Invitation}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), or owner.
	 * 
	 * @param invitation the {@code Invitation}
	 * @param publish true if publish notification
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	@SuppressWarnings("unchecked")
	public void updateInvitation(Invitation invitation, Boolean publish, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.invitation(invitation.getUuid(), config));
		if (publish != null ) {
			Map<String, Object> params = new LinkedHashMap<>();
			if (publish != null) {
				params.put("publish", publish);
			}
			uri = appendQueryParameters(uri, MappingUtils.convert(params, LinkedHashMap.class));
		}
		RequestEntity<Invitation> request = RequestEntity.put(uri).accept(MediaType.APPLICATION_JSON).body(invitation);
		exchange(request, Invitation.class, context);
	}

	/**
	 * Get {@code InvitationStats} with {@code Invitation} statistics.
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN).
	 *
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code InvitationStats}
	 * @throws RestClientException if request fails
	 */
	public InvitationStats getInvitationStats(SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.invitationStats(config));
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<InvitationStats> result = exchange(request, InvitationStats.class, context);
		return result.getBody();
	}

	/**
	 * Get invitation {@code URI} with token for specified {@code Invitation}.
	 * 
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), owner.
	 *
	 * @param id the identifier of the {@code Invitation} (UUID)
	 * @param sendMail true send invatio email
	 * @param context optional {@code SsoClientContext}
	 * @return the invitation token as an {@code URI}
	 * @throws RestClientException if request fails
	 */
	public URI getInvitationToken(String id, Boolean sendMail, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.invitationToken(id, config));
		if (sendMail!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			if (sendMail!=null) {
				params.put("sendMail", sendMail!=null ? Boolean.toString(sendMail) : null);				
			}
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Void> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).build();		
		ResponseEntity<Void> result = exchange(request, Void.class, context);
		return result.getHeaders().getLocation();
	}
	
	/**
	 * Delete existing {@code Invitation}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), or owner.
	 * 
	 * @param id the identifier (UUID)
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void deleteInvitation(String id, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.invitation(id, config));
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class, context);
	}
	
	//
	// Role
	// 
	
	/**
	 * Create a new {@code Role}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN) for global Roles and group Roles prototypes. 
	 * For root {@code Group}s: owner or <b>ROLE_PERMISSION_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or <b>ROLE_PERMISSION_MANAGER</b>, owner or  <b>ROLE_PERMISSION_MANAGER</b> in parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param role the {@code Role}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code Role}
	 * @throws RestClientException if request fails
	 */
	public URI createRole(Role role, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.roles(config));
		RequestEntity<Role> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(role);
		
		ResponseEntity<Role> result = exchange(request, Role.class, context);
		return result.getHeaders().getLocation();
	}
	
	
	/**
	 * Get {@code Role} with specified identifier.
	 * 
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN) for global Roles and group Roles prototypes. 
	 * For root {@code Group}s: owner or <b>ROLE_PERMISSION_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or <b>ROLE_PERMISSION_MANAGER</b>, owner or  <b>ROLE_PERMISSION_MANAGER</b> in parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 *
	 * @param id the {@code Role} identifier (UUID)
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Role}
	 * @throws RestClientException if request fails
	 */
	public Role getRole(String id, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.role(id, config));
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Role> result = exchange(request, Role.class, context);
		return result.getBody();
	}
	
	/**
	 * Update existing {@code Role}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN) for global Roles and group Roles prototypes. 
	 * For root {@code Group}s: owner or <b>ROLE_PERMISSION_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or <b>ROLE_PERMISSION_MANAGER</b>, owner or  <b>ROLE_PERMISSION_MANAGER</b> in parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param role the {@code Role}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void updateRole(Role role, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.role(role.getId(), config));
		RequestEntity<Role> request = RequestEntity.put(uri).accept(MediaType.APPLICATION_JSON).body(role);
		
		exchange(request, Role.class, context);
	}
	
	/**
	 * List {@code Role}s.
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN) for global Roles and group Roles prototypes. 
	 * For root {@code Group}s: owner or <b>ROLE_PERMISSION_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or <b>ROLE_PERMISSION_MANAGER</b>, owner or  <b>ROLE_PERMISSION_MANAGER</b> in parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param filter a {@code RoleFilter}
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Invitation}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Role> listRoles(RoleFilter filter, Pageable pageable, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.roles(config));
		if (filter!=null || pageable!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			if (filter!=null) {
				params.putAll(MappingUtils.toMapFormatted(filter));
			}
			if (pageable!=null) {
				params.putAll(MappingUtils.toMapFormatted(new PageOptions(pageable)));
			}
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		@SuppressWarnings("rawtypes")
		ResponseEntity<PageResult> result = exchange(request, PageResult.class, context);
		return PageUtil.create2(result.getBody(),  Role.class);
	}
	
	/**
	 * Delete existing {@code Role}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN) for global Roles and group Roles prototypes. 
	 * For root {@code Group}s: owner or <b>ROLE_PERMISSION_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or <b>ROLE_PERMISSION_MANAGER</b>, owner or  <b>ROLE_PERMISSION_MANAGER</b> in parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param id the {@code Role} identifier (UUID)
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void deleteRole(String id, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.role(id, config));
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		
		exchange(request, Void.class, context);
	}
	
	//
	// Role Assignments
	//
	
	/**
	 * List {@code User}s assigned a {@code Role} .
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN) for global Roles and group Roles prototypes. 
	 * For root {@code Group}s: owner or <b>ROLE_PERMISSION_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or <b>ROLE_PERMISSION_MANAGER</b>, owner or  <b>ROLE_PERMISSION_MANAGER</b> in parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param roleId the {@code Role} identifier (UUID)
	 * @param pageable a {@code Pageable} (optional)
	 * @param filter a {@code UserFilter} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code User}s
	 * @throws RestClientException if request fails
	 */	
	public Page<User> listRoleMembers(String roleId, Pageable pageable, UserFilter filter, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.roleMembers(roleId, config));
		if (filter!=null || pageable!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			if (filter!=null) {
				params.putAll(MappingUtils.toMapFormatted(filter));
			}
			if (pageable!=null) {
				params.putAll(MappingUtils.toMapFormatted(new PageOptions(pageable)));
			}
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		@SuppressWarnings("rawtypes")
		ResponseEntity<PageResult> result = exchange(request, PageResult.class, context);
		return PageUtil.create2(result.getBody(),  User.class);
	}

	/**
	 * Get count of {@code User}s assigned a {@code Role} .
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN) for global Roles and group Roles prototypes. 
	 * For root {@code Group}s: owner or <b>ROLE_PERMISSION_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or <b>ROLE_PERMISSION_MANAGER</b>, owner or  <b>ROLE_PERMISSION_MANAGER</b> in parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param roleId the {@code Role} identifier (UUID)
	 * @param filter a {@code UserFilter} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code User} count
	 * @throws RestClientException if request fails
	 */	
	public Integer countRoleMembers(String roleId, UserFilter filter, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.countRoleMembers(roleId, config));
		if (filter!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			if (filter!=null) {
				params.putAll(MappingUtils.toMapFormatted(filter));
			}
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Integer> result = exchange(request, Integer.class, context);
		return result.getBody();
	}

	/**
	 * Assign {@code Role} to {@code User}
	 * 
	 * Request ignored if {@code User} is already assigned the {@code Role}.
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), any for root {@code Group}s. 
	 * For sub-{@code Group}s: owner or <b>ROLE_GROUP_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param roleId the {@code Role} identifier (UUID)
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void assignRole(String userId, String roleId, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.roleMembers(roleId, config) + "?username=" + userId);
		userId = encodeId(userId);
		RequestEntity<Void> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class, context);
	}
	
	/**
	 * Unassign {@code Role} from {@code User}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), owner {@code User}.
	 * For root {@code Group}s: owner or <b>ROLE_GROUP_MANAGER</b> in {@code Group}
	 * For sub-{@code Group}s: owner or <b>ROLE_GROUP_MANAGER</b> in {@code Group}, owner or <b>ROLE_GROUP_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param roleId the {@code Role} identifier (UUID)
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void unassignRole(String userId, String roleId, SsoClientContext context) {
		userId = encodeId(userId);
		URI uri = makeURI(SsoEndpoints.roleMembers(roleId, config) + "?username=" + userId);
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class, context);
	}
	
	
	/**
	 * List global {@code Role}s a {@code User} is assigned to.
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), owner {@code User}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Group}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Role> listRolesForUser(String userId, Pageable pageable, SsoClientContext context) {
		RoleFilter filter = new RoleFilter();
		userId = encodeId(userId);
		filter.setUser(userId);
		return listRoles(filter, pageable, context);
	}


	/**
	 * List all global {@code Role}s a {@code User} is assigned to.
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), owner {@code User}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Group}s
	 * @throws RestClientException if request fails
	 */	
	public List<Role> listRolesForUser(String userId, SsoClientContext context) {
		Page<Role> page = listRolesForUser(userId, new PageRequest(0, Integer.MAX_VALUE), context);
		return page!=null ? page.getContent() : null;
	}

	/**
	 * List {@code Role}s a {@code User} is assigned to in a {@code Group}.
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), owner {@code User}.
	 * For root {@code Group}s: owner or <b>ROLE_GROUP_MANAGER</b> in {@code Group}
	 * For sub-{@code Group}s: owner or <b>ROLE_GROUP_MANAGER</b> in {@code Group}, owner or <b>ROLE_GROUP_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param groupId the identifier of a {@code Group} (UUID)
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Group}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Role> listRolesForUserInGroup(String userId, String groupId, Pageable pageable, SsoClientContext context) {
		RoleFilter filter = new RoleFilter();
		userId = encodeId(userId);
		filter.setUser(userId);
		filter.setGroup(groupId);
		return listRoles(filter, pageable, context);
	}

	/**
	 * List all {@code Role}s a {@code User} is assigned to in a {@code Group}.
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin (global ROLE_ADMIN), owner {@code User}.
	 * For root {@code Group}s: owner or <b>ROLE_GROUP_MANAGER</b> in {@code Group}
	 * For sub-{@code Group}s: owner or <b>ROLE_GROUP_MANAGER</b> in {@code Group}, owner or <b>ROLE_GROUP_MANAGER</b> of parent {@code Group},  or owner or <b>ROLE_GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param groupId the identifier of a {@code Group} (UUID)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Group}s
	 * @throws RestClientException if request fails
	 */	
	public List<Role> listRolesForUserInGroup(String userId, String groupId, SsoClientContext context) {
		Page<Role> page = listRolesForUserInGroup(userId, groupId, (Pageable)null, context);
		return page!=null ? page.getContent() : null;
	}

	//
	// Client
	//
	
	/**
	 * Get {@code Client} with specified identifier.
	 * 
	 * <b>Required Security Credentials</b>: Admin (global ROLE_ADMIN).
	 * 
	 * @param id the identifier (UUID)
	 * @param options {@code ClientOptions} options (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Client}
	 * @throws RestClientException if request fails
	 */
	public Client getClient(String id, ClientOptions options, SsoClientContext context) {
		id = encodeId(id);
		URI uri = makeURI(SsoEndpoints.client(id, config));
		if (options!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			params.putAll(MappingUtils.toMapFormatted(options));
			uri = appendQueryParameters(uri, params);			
		}

		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Client> result = exchange(request, Client.class, context);
		return result.getBody();
	}

	/**
	 * List {@code Client}s.
	 * 
	 * <b>Required Security Credentials</b>:  any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param filter a {@code ClientFilter} (optional)
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Client}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Client> listClients(ClientFilter filter, Pageable pageable, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.clients(config));
		if (filter!=null || pageable!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			if (filter!=null) {
				params.putAll(MappingUtils.toMapFormatted(filter));
			}
			if (pageable!=null) {
				params.putAll(MappingUtils.toMapFormatted(new PageOptions(pageable)));
			}
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		@SuppressWarnings("rawtypes")
		ResponseEntity<PageResult> result = exchange(request, PageResult.class, context);
		return PageUtil.create2(result.getBody(),  Client.class);
	}

	/**
	 * Create a new {@code Client}
	 * 
	 * <b>Required Security Credentials</b>: Admin (global ROLE_ADMIN).
	 * 
	 * @param client the {@code Client}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code Client}
	 * @throws RestClientException if request fails
	 */
	public URI createClient(Client client, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.clients(config));
		RequestEntity<Client> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(client);
		
		ResponseEntity<Void> result = exchange(request, Void.class, context);
		return result.getHeaders().getLocation();
	}
	
	/**
	 * Update existing {@code Client}
	 * 
	 * <b>Required Security Credentials</b>: Admin (global ROLE_ADMIN).
	 * 
	 * @param client the {@code Client}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void updateClient(Client client, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.client(client.getId(), config));
		RequestEntity<Client> request = RequestEntity.put(uri).accept(MediaType.APPLICATION_JSON).body(client);
		
		exchange(request, Client.class, context);
	}
	
	/**
	 * Delete existing {@code Client}
	 * 
	 * <b>Required Security Credentials</b>: Admin (global ROLE_ADMIN).
	 * 
	 * @param clientId the {@code Client}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void deleteClient(String clientId, SsoClientContext context) {
		clientId = encodeId(clientId);
		URI uri = makeURI(SsoEndpoints.client(clientId, config));
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		
		exchange(request, Void.class, context);
	}

	//
	// HTTP Transport
	//
	
	
	/**
	 * Submit HTTP request.
	 * 
	 * If {@code context} is not null, use provided {@code OAuth2RestTemplate} if any.
	 * Otherwise, use session scoped {@code OAuth2RestTemplate} if in web request thread. 
	 * Otherwise, use client credentials singleton (non thread-safe) @code OAuth2RestTemplate}.
	 * 
	 * @param <T> response type
	 * @param request the {@code RequestEntity}
	 * @param responseType the response type
	 * @param context optional {@code SsoClientContext}
	 * @return result {@code ResponseEntity}
	 * @throws RestClientException if request fails
	 */
	protected <T> ResponseEntity<T> exchange(RequestEntity<?> request, Class<T> responseType, SsoClientContext context) throws RestClientException {
		OAuth2RestTemplate restTemplate = this.restTemplate;
		if (context!=null && context.getRestTemplate()!=null) {
			restTemplate = context.getRestTemplate();
		} else {
			if (WebUtil.getHttpServletRequest()==null) {
				if (this.restTemplate0==null) {
					this.restTemplate0 = makeClientOAuth2RestTemplate();
				}
				restTemplate = this.restTemplate0;
			}			
		}
		try {
			return exchange(restTemplate, request, responseType);			
		} catch (RuntimeException e) {
			if (context!=null && !context.isSingleton()) {
				context.setResult(new Result<Object>(e));
			}
			throw e;
		}
	}

	/**
	 * Submit HTTP request.
	 * 
	 * May be overriden by sub-classes for custom/advanced functionality.
	 * 
	 * @param <T> response type
	 * @param restTemplate the {@code OAuth2RestTemplate} to use
	 * @param request the {@code RequestEntity}
	 * @param responseType the response type
	 * @return the result {@code ResponseEntity}
	 * @throws RestClientException if request fails
	 */
	protected <T> ResponseEntity<T> exchange(OAuth2RestTemplate restTemplate, RequestEntity<?> request, Class<T> responseType) throws RestClientException {
		if (autoSetupToken) {
			setupToken();
		}
		
		return restTemplate.exchange(request, responseType);
	}

	//
	// OAuth2RestTemplate utils
	// 

	/**
	 * Make a {@code OAuth2RestTemplate} to connect to server specified by a {@code OAuth2ProtectedResourceDetails}.
	 * 
	 * @param resource the {@code OAuth2ProtectedResourceDetails}
	 * @param oauth2ClientContext the {@code OAuth2ClientContext}
	 * @return the {@code OAuth2RestTemplate}
	 */
	public OAuth2RestTemplate makeOAuth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext oauth2ClientContext) {
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource, oauth2ClientContext);			
		template.setRequestFactory(clientHttpRequestFactory);
		return template;
	}
	
	/**
	 * Make a {@code OAuth2RestTemplate} to connect to default server.
	 * 
	 * @param oauth2ClientContext the {@code OAuth2ClientContext}
	 * @return the {@code OAuth2RestTemplate}
	 */
	public OAuth2RestTemplate makeOAuth2RestTemplate(OAuth2ClientContext oauth2ClientContext) {
		return makeOAuth2RestTemplate(config, oauth2ClientContext);
	}
	
	/**
	 * Make a {@code OAuth2RestTemplate} to connect to server specified by configuration {@code SsoClientConfiguration}.
	 * 
	 * @param config the {@code SsoClientConfiguration}
	 * @param oauth2ClientContext the {@code OAuth2ClientContext}
	 * @return the {@code OAuth2RestTemplate}
	 */
	public OAuth2RestTemplate makeOAuth2RestTemplate(SsoClientConfiguration config, OAuth2ClientContext oauth2ClientContext) {
		ClientCredentialsResourceDetails resource = makeClientCredentialsResourceDetails(config);
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource, oauth2ClientContext);			
		template.setRequestFactory(clientHttpRequestFactory);
		return template;
	}
	
	/**
	 * Make a {@code OAuth2RestTemplate} to connect to default server with Client credentials in singleton {@code OAuth2ClientContext}.
	 * 
	 * Sets the created {@code OAuth2RestTemplate} as value of property {@code restTemplate0} if currently null.
	 * 
	 * @return the {@code OAuth2RestTemplate}
	 */
	public OAuth2RestTemplate makeClientOAuth2RestTemplate() {
		if (restTemplate0==null) {
			restTemplate0 = makeOAuth2RestTemplate(oauth2ClientContext0);
		}
		return restTemplate0;
	}
	
	//
	// Client Resource factory methods
	//
	
	public ClientCredentialsResourceDetails makeClientCredentialsResourceDetails(String clientId, String clientSecret) {
		return makeClientCredentialsResourceDetails(clientId, clientSecret, config);
	}

	public ClientCredentialsResourceDetails makeClientCredentialsResourceDetails() {
		return makeClientCredentialsResourceDetails(config);
	}

	public static ClientCredentialsResourceDetails makeClientCredentialsResourceDetails(SsoClientConfiguration config) {
		return makeClientCredentialsResourceDetails(config.getClientId(), config.getClientSecret(), config);
	}


	public static ClientCredentialsResourceDetails makeClientCredentialsResourceDetails(String clientId,
			String clientSecret, SsoClientConfiguration config) {
		ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
		resource.setClientId(clientId);
		resource.setClientSecret(clientSecret);
		resource.setAccessTokenUri(SsoEndpoints.getTokenEndpoint(config));
		resource.setTokenName(SsoClientConfiguration.OAUTH_TOKEN_NAME);
		resource.setAuthenticationScheme(AuthenticationScheme.header);
		resource.setClientAuthenticationScheme(AuthenticationScheme.header);
		resource.setScope(config.getScopes());
		return resource;
	}

	//
	// Client Credentials Token utils
	//
	
	public OAuth2AccessToken setupClientToken() {
		return setupClientToken(oauth2ClientContext);
	}


	public OAuth2AccessToken setupClientToken0() {
		return setupClientToken(oauth2ClientContext0);
	}

	public OAuth2AccessToken setupClientToken(OAuth2ClientContext oauth2ClientContext) {
		return setupClientToken(oauth2ClientContext, false, false, config);
	}

	public OAuth2AccessToken setupClientToken(boolean force, boolean cached) {
		return setupClientToken(oauth2ClientContext, force, cached);
	}

	public OAuth2AccessToken setupClientToken(String clientId, String clientSecret) {
		SsoClientConfiguration config2 = new SsoClientConfiguration(config);
		config2.setClientId(clientId);
		config2.setClientSecret(clientSecret);
		return setupClientToken(oauth2ClientContext, config2);
	}

	public OAuth2AccessToken setupClientToken(OAuth2ClientContext oauth2ClientContext, boolean force, boolean cached) {
		return setupClientToken(oauth2ClientContext, force, cached, config);
	}

	public static OAuth2AccessToken setupClientToken(OAuth2ClientContext oauth2ClientContext, SsoClientConfiguration config) {
		return setupClientToken(oauth2ClientContext, false, false, config);
	}

	public static OAuth2AccessToken setupClientToken(OAuth2ClientContext oauth2ClientContext, boolean force, boolean cached, SsoClientConfiguration config) {
		OAuth2AccessToken token = null;
		if (!force) {
			token = oauth2ClientContext.getAccessToken();
			if (token != null) {
				return token;
			}
		}
		token = getClientToken(oauth2ClientContext, config);

		if (token == null) {
			return null;
		}
		oauth2ClientContext.setAccessToken(token);

		return token;
	}

	public static OAuth2RestTemplate makeClientRestTemplate(SsoClientConfiguration config) {
		return makeClientRestTemplate(config, true);
	}

	public static OAuth2RestTemplate makeClientRestTemplate(SsoClientConfiguration config, boolean setup) {
		ClientCredentialsResourceDetails credentials = makeClientCredentialsResourceDetails(config);
		OAuth2ClientContext context = new DefaultOAuth2ClientContext();
		OAuth2RestTemplate template = new OAuth2RestTemplate(credentials, context);
		if (setup) {
			setupClientToken(template.getOAuth2ClientContext(), config);			
		}
		return template;
		
	}

	public static ResourceOwnerPasswordResourceDetails makeResourceOwnerPasswordResourceDetails(String username,
			String password, SsoClientConfiguration config) {
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
		resource.setClientId(config.getClientId());
		resource.setClientSecret(config.getClientSecret());
		resource.setAccessTokenUri(SsoEndpoints.getTokenEndpoint(config));
		resource.setTokenName(SsoClientConfiguration.OAUTH_TOKEN_NAME);
		resource.setAuthenticationScheme(AuthenticationScheme.header);
		resource.setClientAuthenticationScheme(AuthenticationScheme.header);
		resource.setUsername(username);
		resource.setPassword(password);
		resource.setScope(config.getScopes());
		return resource;
	}

	public ResourceOwnerPasswordResourceDetails makeResourceOwnerPasswordResourceDetails(String username, String password) {
		return makeResourceOwnerPasswordResourceDetails(username, password, config);
	}
	

	public static OAuth2AccessToken getClientToken(OAuth2ClientContext oauth2ClientContext, SsoClientConfiguration config) {
		ClientCredentialsResourceDetails resource = makeClientCredentialsResourceDetails(config.getClientId(), config.getClientSecret(), config);
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource, oauth2ClientContext);
		OAuth2AccessToken token = template.getAccessToken();
		return token;
	}

	public OAuth2AccessToken getClientToken(OAuth2ClientContext oauth2ClientContext) {
		return getClientToken(oauth2ClientContext, config);
	}
	
	//
	// Token utils
	//
	
	public OAuth2AccessToken setupToken() {
		return setupToken(false);
	}

	public OAuth2AccessToken setupToken(boolean force) {
		OAuth2AccessToken token = null;

		if (!force) {
			token = oauth2ClientContext.getAccessToken();
			if (token != null) {
				return token;
			}
		}

		token = getToken();
		if (token == null) {
			logger.warn("setupToken: No token found");
			return null;
		}
		oauth2ClientContext.setAccessToken(token);
		return token;
	}

	public OAuth2AccessToken getToken(String username, String password) {
		return getToken(username, password, oauth2ClientContext);
	}

	public OAuth2AccessToken getToken(String username, String password, OAuth2ClientContext oauth2ClientContext) {
		ResourceOwnerPasswordResourceDetails resource = makeResourceOwnerPasswordResourceDetails(username, password);
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource, oauth2ClientContext);
		OAuth2AccessToken token = template.getAccessToken();
		return token;
	}


	public static OAuth2AccessToken getToken(Authentication authentication) {
		String tokenValue = getTokenValue(authentication);
		if (tokenValue == null) {
			return null;
		}
		return new DefaultOAuth2AccessToken(tokenValue);
	}

	public static OAuth2AccessToken getToken(Principal principal) {
		String tokenValue = getTokenValue(principal);
		if (tokenValue == null) {
			return null;
		}
		return new DefaultOAuth2AccessToken(tokenValue);
	}

	public static OAuth2AccessToken getToken() {
		return getToken(SecurityUtil.getAuthentication());
	}

	public OAuth2AccessToken getSessionToken() {
		return oauth2ClientContext.getAccessToken();
	}

	public static String getTokenValue() {
		return getTokenValue(SecurityUtil.getAuthentication());
	}

	public static String getTokenValue(Principal principal) {
		if (principal == null) {
			return null;
		}
		if (!(principal instanceof Authentication)) {
			return null;
		}
		return getTokenValue((Authentication) principal);
	}

	public static String getTokenValue(Authentication authentication) {
		if (!(authentication instanceof OAuth2Authentication)) {
			return null;
		}
		OAuth2Authentication oauth2 = (OAuth2Authentication) authentication;
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) oauth2.getDetails();
		if (details == null) {
			return null;
		}
		return details.getTokenValue();
	}

	public static String getTokenType(Principal principal) {
		if (principal == null) {
			return null;
		}
		if (!(principal instanceof OAuth2Authentication)) {
			return null;
		}
		OAuth2Authentication oauth2 = (OAuth2Authentication) principal;
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) oauth2.getDetails();
		return details.getTokenType();
	}

	public static String getSessionId(Principal principal) { // Otka only
		if (principal == null) {
			return null;
		}
		if (!(principal instanceof OAuth2Authentication)) {
			return null;
		}
		OAuth2Authentication oauth2 = (OAuth2Authentication) principal;
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) oauth2.getDetails();
		return details.getSessionId();
	}


	//
	// Factory utils
	//
	
	public static SsoClient makeSsoClient(SsoClientConfiguration config) {
		OAuth2ClientContext context = new DefaultOAuth2ClientContext();
		OAuth2ProtectedResourceDetails resource = SsoClient.makeClientCredentialsResourceDetails(config);
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource, context);
		SsoClient ssoClient = new SsoClient(template, config);
		ssoClient.init();
		return ssoClient;
	}

	public static SsoClient makeSsoClient(String username, String password, SsoClientConfiguration config) {
		OAuth2ClientContext context = new DefaultOAuth2ClientContext();
		OAuth2ProtectedResourceDetails resource = SsoClient.makeResourceOwnerPasswordResourceDetails(username, password,
				config);
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource, context);
		SsoClient ssoClient = new SsoClient(template, config);
		ssoClient.init();
		return ssoClient;
	}

	//
	// Principal utils
	//
	
	/**
	 * Get {@code User} for {@code Principal} in {@code SessionContext} using local context data initialized from <b>/userinfo</b> endpoint of server.
	 * 
	 * @return the {@code User}
	 */
	public static final User getPrincipalUser() {
		 Map<String, Object> details = SecurityUtil.getPrincipalDetails();
		 return MappingUtils.convert(details, User.class);
	}
	
	//
	// Logout
	//
	
	
	/**
	 * Force logout.
	 * 
	 * @param context optional {@code SsoClientContext}
	 */
	public void doLogout(SsoClientContext context) {
		@SuppressWarnings("rawtypes")
		RequestEntity request2 = RequestEntity.post(makeURI(SsoEndpoints.getTokenRevokeEndpoint(config)))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).build();
		try {
			exchange(request2, Void.class, context);
			if (logger.isDebugEnabled()) {
				logger.debug("logout:");					
			}
		} catch (RuntimeException e) {
			logger.error("logout:" + e);
		}
	}

	/**
	 * Force logout.
	 * 
	 * @param authentication the {@code Authentication}
	 * @param context optional {@code SsoClientContext}
	 */
	public void doLogout(Authentication authentication, SsoClientContext context) {
		if (authentication == null) {
			return;
		}
		Object details = authentication.getDetails();
		if (details.getClass().isAssignableFrom(OAuth2AuthenticationDetails.class)) {
			String accessToken = ((OAuth2AuthenticationDetails) details).getTokenValue();
			SsoClientConfiguration config = this.config;
			if (context!=null && context.getConfig()!=null) {
				config = context.getConfig();
			}
			@SuppressWarnings("rawtypes")
			RequestEntity request2 = RequestEntity.post(makeURI(SsoEndpoints.getTokenRevokeEndpoint(config)))
					.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", "Bearer " + accessToken).build();
			try {
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.exchange(request2, Void.class);
				if (logger.isDebugEnabled()) {
					logger.debug("logout:");					
				}
			} catch (RuntimeException e) {
				logger.error("logout:" + e);
			}
		}
	}

	/**
	 * Force logout.
	 * 
	 * @param session the {@code HttpSession}
	 */
	public void doLogout(HttpSession session) {
		if (session != null) {
			logger.debug("Invalidating session: " + session.getId());
			session.invalidate();
		}
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			context.setAuthentication(null);
			SecurityContextHolder.clearContext();
		}
	}

	
}
