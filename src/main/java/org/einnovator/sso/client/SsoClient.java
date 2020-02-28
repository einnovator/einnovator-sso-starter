package org.einnovator.sso.client;

import static org.einnovator.util.UriUtils.appendQueryParameters;
import static org.einnovator.util.UriUtils.encode;
import static org.einnovator.util.UriUtils.encodeId;
import static org.einnovator.util.UriUtils.makeURI;

import java.net.URI;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.sso.client.config.SsoClientConfiguration;
import org.einnovator.sso.client.config.SsoClientContext;
import org.einnovator.sso.client.config.SsoEndpoints;
import org.einnovator.sso.client.model.Client;
import org.einnovator.sso.client.model.Group;
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
import org.einnovator.sso.client.modelx.RoleOptions;
import org.einnovator.sso.client.modelx.UserFilter;
import org.einnovator.sso.client.modelx.UserOptions;
import org.einnovator.util.MappingUtils;
import org.einnovator.util.PageOptions;
import org.einnovator.util.PageResult;
import org.einnovator.util.PageUtil;
import org.einnovator.util.model.Application;
import org.einnovator.util.security.SecurityUtil;
import org.einnovator.util.web.ClientContext;
import org.einnovator.util.web.RequestOptions;
import org.einnovator.util.web.Result;
import org.einnovator.util.web.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Client to SSO Gateway.
 * 
 * <p>Provide methods for all server endpoints and resource types. 
 * <p>Including: {@link User}, {@link Group}, {@link Member}, {@link Invitation}, {@link Role}, {@link Client}
 * <p>Errors are propagated using Java runtime exceptions.
 * <p>For caching enabled "high-level" API, see Manager classes.
 * <p>{@code SsoClientConfiguration} specifies configuration details, including server URL and client credentials.
 * <p>Property {@link #getConfig()} provides the default {@code SsoClientConfiguration} to use.
 * <p>All API methods that invoke a server endpoint accept an <em>optional</em> tail parameter to connect to alternative server
 *  (e.g. for cover the less likely case where an application need to connect to multiple servers in different clusters).
 * <p>Internally, {@code SsoClient} uses a {@code OAuth2RestTemplate} to invoke remote server.
 * <p>When setup as a <b>Spring Bean</b> both {@code SsoClientConfiguration} and {@code OAuth2RestTemplate} are auto-configured.
 * <p>Requests use a session-scoped  {@code OAuth2ClientContext} if running in a web-environment.
 * <p>If the invoking thread does not have an associated web session, the default behavior is to fallback to use a {@code OAuth2ClientContext} 
 * with client credentials. This can be disabled by setting property {@link #web} to false.
 * <p>Method {@link #register()} can be used to register custom application roles with server.
 * <p>This is automatically performed by if configuration property {@code sso.registration.roles.auto} is set to true.
 * 
 * @see org.einnovator.sso.client.manager.UserManager
 * @see org.einnovator.sso.client.manager.GroupManager
 * @see org.einnovator.sso.client.manager.RoleManager
 * @see org.einnovator.sso.client.manager.InvitationManager
 * @see org.einnovator.sso.client.manager.ClientManager
 * 
 * @author support@einnovator.org
 *
 */
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

	private boolean autoSetupToken;
	
	@Autowired(required=false)
	private Application application;

	private boolean web = true;
	
	/**
	 * Create instance of {@code SsoClient}.
	 *
	 * @param config the {@code SsoClientConfiguration}
	 */
	@Autowired
	public SsoClient(SsoClientConfiguration config) {
		this.config = config;
	}

	/**
	 * Create instance of {@code SsoClient}.
	 *
	 * @param restTemplate the {@code OAuth2RestTemplate} used for HTTP transport
	 * @param config the {@code SsoClientConfiguration}
	 */
	public SsoClient(OAuth2RestTemplate restTemplate, SsoClientConfiguration config) {
		this.config = config;
		this.restTemplate = restTemplate;
		this.oauth2ClientContext = restTemplate.getOAuth2ClientContext();
	}

	/**
	 * Create instance of {@code SsoClient}.
	 *
	 * @param restTemplate the {@code OAuth2RestTemplate} used for HTTP transport
	 * @param config the {@code SsoClientConfiguration}
	 * @param web true if auto-detect web-environment 
	 */
	public SsoClient(OAuth2RestTemplate restTemplate, SsoClientConfiguration config, boolean web) {
		this(restTemplate, config);
		this.web = web;
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

	/**
	 * Get the value of property {@code web}.
	 *
	 * @return the web
	 */
	public boolean isWeb() {
		return web;
	}

	/**
	 * Set the value of property {@code web}.
	 *
	 * @param web the value of property web
	 */
	public void setWeb(boolean web) {
		this.web = web;
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
	 * Register client application data with default server using client credentials.
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
	 * <p><b>Required Security Credentials</b>: Client or Admin (global role ADMIN).
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
	 * <p><b>Required Security Credentials</b>: Any, but results depend on each {@code User} privacy settings.
	 * 
	 * @param id the identifier
	 * @param options (optional) the {@code UserOptions} that tailor which fields are returned (projection)
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code User}
	 * @throws RestClientException if request fails
	 */
	public User getUser(String id, UserOptions options, SsoClientContext context) {
		id = encodeId(id);
		URI uri = makeURI(SsoEndpoints.user(id, config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<User> result = exchange(request, User.class, context);
		return result.getBody();
	}
	
	
	/**
	 * List {@code User}s.
	 * 
	 * <p><b>Required Security Credentials</b>: Any, but results depend on credentials and each {@code User} privacy settings.
	 * 
	 * @param filter a {@code UserFilter}
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 * @return a {@code Page} with {@code User}s
	 */
	public Page<User> listUsers(UserFilter filter, Pageable pageable, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.users(config, isAdminRequest(filter, context)));
		uri = processURI(uri, filter, pageable);
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		@SuppressWarnings("rawtypes")
		ResponseEntity<PageResult> result = exchange(request, PageResult.class, context);
		return PageUtil.create2(result.getBody(), User.class);
	}


	/**
	 * Create a new {@code User}.
	 * 
	 * 
	 * <p><b>Required Security Credentials</b>: Client or Admin (global role ADMIN).
	 * 
	 * @param user the {@code User}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code User}
	 * @throws RestClientException if request fails
	 */
	public URI createUser(User user, RequestOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.users(config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<User> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(user);
		ResponseEntity<Void> result = exchange(request, Void.class, context);
		return result.getHeaders().getLocation();
	}
	
	/**
	 * Update existing {@code User}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), or owner.
	 * 
	 * @param user the {@code User}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void updateUser(User user, RequestOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.user(user.getId(), config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<User> request = RequestEntity.put(uri).accept(MediaType.APPLICATION_JSON).body(user);
		exchange(request, User.class, context);
	}
	
	/**
	 * Delete existing {@code User}
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID, username, email.
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), or owner.
	 * 
	 * @param id the {@code User} identifier
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void deleteUser(String id, RequestOptions options, SsoClientContext context) {
		id = encodeId(id);
		URI uri = makeURI(SsoEndpoints.user(id, config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class, context);
	}
	

	//
	// Password
	// 

	/**
	 * Change a {@code User} password.
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), owner {@code User}.
	 * 
	 * @param password the password
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void changePassword(String password, RequestOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.password(config, isAdminRequest(options, context)) + "?password=" + password);
		uri = processURI(uri, options);
		RequestEntity<Void> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class, context);
	}

	
	//
	// Group
	//
	

	/**
	 * Get {@code Group} with specified identifier.
	 * 
	 * Identifier {@code id} is the value of a property with unique constraints, that is:
	 * UUID, name for root {@code Groups} if server is configured to required unique names for root {@code Group}s .
	 * 
	 * <p><b>Required Security Credentials</b>: any for root {@code Group}, but results depend on each {@code User} privacy settings.
	 *
	 * @param groupId the identifier
	 * @param filter (optional) the {@code GroupOptions} that tailor which fields are returned (projection) and {@code GroupFilter} for sub-groups
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Group}
	 * @throws RestClientException if request fails
	 */
	public Group getGroup(String groupId, GroupFilter filter, SsoClientContext context) {
		groupId = encode(groupId);
		URI uri = makeURI(SsoEndpoints.group(groupId, config, isAdminRequest(filter, context)));
		uri = processURI(uri, filter);
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Group> result = exchange(request, Group.class, context);
		return result.getBody();
	}
	
	/**
	 * List {@code Group}s.
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param filter a {@code GroupFilter}
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Group}s
	 * @throws RestClientException if request fails
	 */
	public Page<Group> listGroups(GroupFilter filter, Pageable pageable, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.groups(config, isAdminRequest(filter, context)));
		uri = processURI(uri, filter, pageable);
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		@SuppressWarnings("rawtypes")
		ResponseEntity<PageResult> result = exchange(request, PageResult.class, context);
		return PageUtil.create2(result.getBody(), Group.class);
	}
	
	/**
	 * Create a new {@code Group}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), any for root {@code Group}s. 
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param group the {@code Group}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code User}
	 * @throws RestClientException if request fails
	 */
	public URI createGroup(Group group, RequestOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.groups(config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Group> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(group);
		ResponseEntity<Group> result = exchange(request, Group.class, context);
		return result.getHeaders().getLocation();
	}

	/**
	 * Update existing {@code Group}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN).
	 * <p>For root {@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}, owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param group the {@code Group}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void updateGroup(Group group, RequestOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.group(encode(group.getId()), config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Group> request = RequestEntity.put(uri).accept(MediaType.APPLICATION_JSON).body(group);		
		exchange(request, Group.class, context);
	}

	/**
	 * Delete existing {@code Group}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN).
	 * <p>For root {@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}, owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param id the {@code Group} identifier
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void deleteGroup(String id, RequestOptions options, SsoClientContext context) {
		id = encode(id);
		URI uri = makeURI(SsoEndpoints.group(id, config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class, context);
	}
	
	//
	// Group Tree
	//

	/**
	 * List sub-{@code Group}s.
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param id the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param direct true if count only direct sub-group, false if count the all tree
	 * @param filter a {@code GroupFilter} to filter sub-groups
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Group}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Group> listSubGroups(String id, boolean direct, GroupFilter filter, Pageable pageable, SsoClientContext context) {
		id = encode(id);
		if (filter==null) {
			filter = new GroupFilter();			
		}
		if (direct) {
			filter.setParent(id);			
		} else {
			filter.setRoot(id);
		}
		return listGroups(filter, pageable, context);
	}

	/**
	 * Count {@code Group}s matching specified {@code GroupFilter}.
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param filter a {@code GroupFilter}
	 * @param context optional {@code SsoClientContext}
	 * @return the count of {@code Group}s
	 * @throws RestClientException if request fails
	 */	
	public Integer countGroups(GroupFilter filter, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.countGroups(config, isAdminRequest(filter, context)));
		uri = processURI(uri, filter);
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Integer> result = exchange(request, Integer.class, context);
		return result.getBody();
	}
	
	/**
	 * Count number of sub-{@code Group}s for specified {@code Group}.
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param id the {@code id} identifier (UUID, or name of root group if supported)
	 * @param direct true if count only direct sub-group, false if count the all tree
	 * @param filter a {@code GroupFilter}
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Group}s
	 * @throws RestClientException if request fails
	 */	
	public Integer countSubGroups(String id, boolean direct, GroupFilter filter, SsoClientContext context) {
		if (filter==null) {
			filter = new GroupFilter();			
		}
		if (direct) {
			filter.setParent(id);			
		} else {
			filter.setRoot(id);
		}
		return countGroups(filter, context);
	}

	//
	// Group Members
	//
	
	/**
	 * List {@code Member} of a {@code Group} .
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings,
	 * and each {@code User} privacy settings.
	 * 
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param filter a {@code MemberFilter}
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Member}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Member> listGroupMembers(String groupId, MemberFilter filter, Pageable pageable, SsoClientContext context) {
		groupId = encode(groupId);
		URI uri = makeURI(SsoEndpoints.groupMembers(groupId, config, isAdminRequest(filter, context)));
		uri = processURI(uri, filter, pageable);
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		@SuppressWarnings("rawtypes")
		ResponseEntity<PageResult> result = exchange(request, PageResult.class, context);
		return PageUtil.create2(result.getBody(), Member.class);
	}

	/**
	 * Get count of {@code Member} in a {@code Group} .
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings,
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
		URI uri = makeURI(SsoEndpoints.countMembers(id, config, isAdminRequest(filter, context)));
		uri = processURI(uri, filter);
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
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings,
	 * and each {@code User} privacy settings.
	 * 
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param options optional {@code UserOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Member}
	 * @throws RestClientException if request fails
	 */
	public Member getGroupMember(String groupId, String userId, UserOptions options, SsoClientContext context) {
		groupId = encode(groupId);
		userId = encodeId(userId);
		URI uri = makeURI(SsoEndpoints.member(groupId, userId, config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Member> result = exchange(request, Member.class, context);
		return result.getBody();			
	}

	/**
	 * Add user a new {@code Group}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), any for root {@code Group}s. 
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code Member}
	 * @throws RestClientException if request fails
	 */
	public URI addMemberToGroup(String userId, String groupId, RequestOptions options, SsoClientContext context) {
		groupId = encode(groupId);
		userId = encodeId(userId);
		URI uri = makeURI(SsoEndpoints.groupMembers(groupId, config, isAdminRequest(options, context)) + "?username=" + userId);
		uri = processURI(uri, options);
		RequestEntity<Void> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).build();		
		ResponseEntity<Void> result = exchange(request, Void.class, context);
		return result.getHeaders().getLocation();
	}
	
	/**
	 * Add user a new {@code Group}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), any for root {@code Group}s. 
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param member the {@code Member} to add to Group
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code Member}
	 * @throws RestClientException if request fails
	 */
	public URI addMemberToGroup(Member member, String groupId, RequestOptions options, SsoClientContext context) {
		groupId = encode(groupId);
		URI uri = makeURI(SsoEndpoints.groupMembers(groupId, config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Member> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(member);		
		ResponseEntity<Void> result = exchange(request, Void.class, context);
		return result.getHeaders().getLocation();
	}
	
	/**
	 * Remove {@code User} from a {@code Group}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), owner {@code User}.
	 * <p>For root {@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}, owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param groupId the {@code Group} identifier (UUID, or name of root group if supported)
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void removeMemberFromGroup(String userId, String groupId, RequestOptions options, SsoClientContext context) {
		groupId = encode(groupId);
		userId = encodeId(userId);
		URI uri = makeURI(SsoEndpoints.groupMembers(groupId, config, isAdminRequest(options, context)) + "?username=" + userId);
		uri = processURI(uri, options);
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class, context);
	}
	
	/**
	 * List {@code Group}s a {@code User} is member.
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings,
	 * and each {@code User} privacy settings.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param filter a {@code UserFilter} (optional)
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Group}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Group> listGroupsForUser(String userId,  GroupFilter filter, Pageable pageable, SsoClientContext context) {
		if (filter==null) {
			filter = new GroupFilter();			
		}
		filter.setOwner(userId);
		return listGroups(filter, pageable, context);
	}

	//
	// Invitation
	//
	
	/**
	 * List {@code Invitation}s.
	 * 
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend user, and each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param filter a {@code InvitationFilter}
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Invitation}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Invitation> listInvitations(InvitationFilter filter, Pageable pageable, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.invitations(config, isAdminRequest(filter, context)));
		uri = processURI(uri, filter, pageable);
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		@SuppressWarnings("rawtypes")
		ResponseEntity<PageResult> result = exchange(request, PageResult.class, context);
		return PageUtil.create2(result.getBody(), Invitation.class);
	}

	/**
	 * Get {@code Invitation} with specified identifier.
	 * 
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), owner.
	 *
	 * @param id the identifier (UUID)
	 * @param options optional  {@code InvitationOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Invitation}
	 * @throws RestClientException if request fails
	 */
	public Invitation getInvitation(String id, InvitationOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.invitation(id, config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Invitation> result = exchange(request, Invitation.class, context);
		return result.getBody();
	}

	/**
	 * Create a new {@code Invitation}.
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), any. 
	 * 
	 * @param invitation the {@code Invitation}
	 * @param options the {@code InvitationOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code Invitation}
	 * @throws RestClientException if request fails
	 */
	public URI invite(Invitation invitation, InvitationOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.invite(config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Invitation> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(invitation);		
		ResponseEntity<Void> result = exchange(request, Void.class, context);
		return result.getHeaders().getLocation();
	}
	
	/**
	 * Update existing {@code Invitation}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), or owner.
	 * 
	 * @param invitation the {@code Invitation}
	 * @param options optional  {@code InvitationOptions}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void updateInvitation(Invitation invitation, RequestOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.invitation(invitation.getUuid(), config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Invitation> request = RequestEntity.put(uri).accept(MediaType.APPLICATION_JSON).body(invitation);
		exchange(request, Invitation.class, context);
	}

	
	/**
	 * Get {@code InvitationStats} with {@code Invitation} statistics.
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN).
	 *
	 * @param options optional  {@code InvitationOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code InvitationStats}
	 * @throws RestClientException if request fails
	 */
	public InvitationStats getInvitationStats(RequestOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.invitationStats(config, isAdminRequest(options, context)));
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<InvitationStats> result = exchange(request, InvitationStats.class, context);
		return result.getBody();
	}

	/**
	 * Get invitation {@code URI} with token for specified {@code Invitation}.
	 * 
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), owner.
	 *
	 * @param id the identifier of the {@code Invitation} (UUID)
	 * @param options optional  {@code InvitationOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the invitation token as an {@code URI}
	 * @throws RestClientException if request fails
	 */
	public URI getInvitationToken(String id, InvitationOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.invitationToken(id, config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Void> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).build();		
		ResponseEntity<Void> result = exchange(request, Void.class, context);
		return result.getHeaders().getLocation();
	}
	
	/**
	 * Delete existing {@code Invitation}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), or owner.
	 * 
	 * @param id the identifier (UUID)
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void deleteInvitation(String id, RequestOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.invitation(id, config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class, context);
	}
	
	//
	// Role
	// 
	

	/**
	 * Get {@code Role} with specified identifier.
	 * 
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN) for global Roles and group Roles prototypes. 
	 * <p>For root {@code Group}s: owner or role <b>PERMISSION_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or role <b>PERMISSION_MANAGER</b>, owner or role <b>PERMISSION_MANAGER</b> in parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 *
	 * @param id the {@code Role} identifier (UUID)
	 * @param options the {@code RoleOptions} (options)
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Role}
	 * @throws RestClientException if request fails
	 */
	public Role getRole(String id, RoleOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.role(id, config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Role> result = exchange(request, Role.class, context);
		return result.getBody();
	}
	
	/**
	 * List {@code Role}s.
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN) for global Roles and group Roles prototypes. 
	 * <p>For root {@code Group}s: owner or role <b>PERMISSION_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or role <b>PERMISSION_MANAGER</b>, owner or role <b>PERMISSION_MANAGER</b> in parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param filter a {@code RoleFilter}
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Role}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Role> listRoles(RoleFilter filter, Pageable pageable, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.roles(config, isAdminRequest(filter, context)));
		uri = processURI(uri, filter, pageable);
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		@SuppressWarnings("rawtypes")
		ResponseEntity<PageResult> result = exchange(request, PageResult.class, context);
		return PageUtil.create2(result.getBody(), Role.class);
	}
	
	
	/**
	 * Create a new {@code Role}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN) for global Roles and group Roles prototypes. 
	 * <p>For root {@code Group}s: owner or role <b>PERMISSION_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or role <b>PERMISSION_MANAGER</b>, owner or role <b>PERMISSION_MANAGER</b> in parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param role the {@code Role}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code Role}
	 * @throws RestClientException if request fails
	 */
	public URI createRole(Role role, RequestOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.roles(config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Role> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(role);
		ResponseEntity<Role> result = exchange(request, Role.class, context);
		return result.getHeaders().getLocation();
	}
	
	/**
	 * Update existing {@code Role}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN) for global Roles and group Roles prototypes. 
	 * <p>For root {@code Group}s: owner or role <b>PERMISSION_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or role <b>PERMISSION_MANAGER</b>, owner or role <b>PERMISSION_MANAGER</b> in parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param role the {@code Role}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void updateRole(Role role, RequestOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.role(role.getId(), config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Role> request = RequestEntity.put(uri).accept(MediaType.APPLICATION_JSON).body(role);
		exchange(request, Role.class, context);
	}
	
	
	
	/**
	 * Delete existing {@code Role}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN) for global Roles and group Roles prototypes. 
	 * <p>For root {@code Group}s: owner or role <b>PERMISSION_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or role <b>PERMISSION_MANAGER</b>, owner or role <b>PERMISSION_MANAGER</b> in parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param id the {@code Role} identifier (UUID)
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void deleteRole(String id, RequestOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.role(id, config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class, context);
	}
	
	//
	// Role Bindings/Assignments
	//
	
	/**
	 * List {@code User}s assigned a {@code Role} .
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN) for global Roles and group Roles prototypes. 
	 * <p>For root {@code Group}s: owner or role <b>PERMISSION_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or role <b>PERMISSION_MANAGER</b>, owner or role <b>PERMISSION_MANAGER</b> in parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param roleId the {@code Role} identifier (UUID)
	 * @param filter a {@code UserFilter} (optional)
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code User}s
	 * @throws RestClientException if request fails
	 */	
	public Page<User> listRoleMembers(String roleId, UserFilter filter, Pageable pageable, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.roleMembers(roleId, config, isAdminRequest(filter, context)));
		uri = processURI(uri, filter, pageable);
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		@SuppressWarnings("rawtypes")
		ResponseEntity<PageResult> result = exchange(request, PageResult.class, context);
		return PageUtil.create2(result.getBody(), User.class);
	}

	/**
	 * Get count of {@code User}s assigned a {@code Role} .
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN) for global Roles and group Roles prototypes. 
	 * <p>For root {@code Group}s: owner or role <b>PERMISSION_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * For sub{@code Group}s: owner or role <b>PERMISSION_MANAGER</b>, owner or role <b>PERMISSION_MANAGER</b> in parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param roleId the {@code Role} identifier (UUID)
	 * @param filter a {@code UserFilter} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code User} count
	 * @throws RestClientException if request fails
	 */	
	public Integer countRoleMembers(String roleId, UserFilter filter, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.countRoleMembers(roleId, config, isAdminRequest(filter, context)));
		uri = processURI(uri, filter);
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Integer> result = exchange(request, Integer.class, context);
		return result.getBody();
	}

	/**
	 * Assign {@code Role} to {@code User}
	 * 
	 * Request ignored if {@code User} is already assigned the {@code Role}.
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), any for root {@code Group}s. 
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param roleId the {@code Role} identifier (UUID)
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void assignRole(String userId, String roleId, RequestOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.roleMembers(roleId, config, isAdminRequest(options, context)) + "?username=" + userId);
		userId = encodeId(userId);
		uri = processURI(uri, options);
		RequestEntity<Void> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class, context);
	}
	
	/**
	 * Unassign {@code Role} from {@code User}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), owner {@code User}.
	 * <p>For root {@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}, owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param roleId the {@code Role} identifier (UUID)
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void unassignRole(String userId, String roleId, RequestOptions options, SsoClientContext context) {
		userId = encodeId(userId);
		URI uri = makeURI(SsoEndpoints.roleMembers(roleId, config, isAdminRequest(options, context)) + "?username=" + userId);
		uri = processURI(uri, options);
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class, context);
	}
	
	
	/**
	 * List global {@code Role}s a {@code User} is assigned to.
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), owner {@code User}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param filter a {@code RoleFilter} (optional)
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Role}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Role> listRolesForUser(String userId, RoleFilter filter, Pageable pageable, SsoClientContext context) {
		if (filter==null) {
			filter = new RoleFilter();
		}
		userId = encodeId(userId);
		filter.setRunAs(userId);
		return listRoles(filter, pageable, context);
	}


	/**
	 * List {@code Role}s a {@code User} is assigned to in a {@code Group}.
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), owner {@code User}.
	 * <p>For root {@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}
	 * <p>For sub-{@code Group}s: owner or role <b>GROUP_MANAGER</b> in {@code Group}, owner or role <b>GROUP_MANAGER</b> of parent {@code Group}, or owner or role <b>GROUP_MANAGER</b> of tree root {@code Group}.
	 * 
	 * @param userId the identifier of a {@code User} (UUID, or username)
	 * @param groupId the identifier of a {@code Group} (UUID)
	 * @param filter a {@code RoleFilter} (optional)
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Role}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Role> listRolesForUserInGroup(String userId, String groupId, RoleFilter filter, Pageable pageable, SsoClientContext context) {
		if (filter==null) {
			filter = new RoleFilter();
		}
		userId = encodeId(userId);
		filter.setRunAs(userId);
		filter.setGroup(groupId);
		return listRoles(filter, pageable, context);
	}


	//
	// Client
	//
	
	/**
	 * Get {@code Client} with specified identifier.
	 * 
	 * <p><b>Required Security Credentials</b>: Admin (global role ADMIN).
	 * 
	 * @param id the identifier (UUID)
	 * @param options {@code ClientOptions} options (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code Client}
	 * @throws RestClientException if request fails
	 */
	public Client getClient(String id, ClientOptions options, SsoClientContext context) {
		id = encodeId(id);
		URI uri = makeURI(SsoEndpoints.client(id, config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Client> result = exchange(request, Client.class, context);
		return result.getBody();
	}

	/**
	 * List {@code Client}s.
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend on each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param filter a {@code ClientFilter} (optional)
	 * @param pageable a {@code Pageable} (optional)
	 * @param context optional {@code SsoClientContext}
	 * @return a {@code Page} with {@code Client}s
	 * @throws RestClientException if request fails
	 */	
	public Page<Client> listClients(ClientFilter filter, Pageable pageable, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.clients(config, isAdminRequest(filter, context)));
		uri = processURI(uri, filter, pageable);
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		@SuppressWarnings("rawtypes")
		ResponseEntity<PageResult> result = exchange(request, PageResult.class, context);
		return PageUtil.create2(result.getBody(), Client.class);
	}

	/**
	 * Create a new {@code Client}
	 * 
	 * <p><b>Required Security Credentials</b>: Admin (global role ADMIN).
	 * 
	 * @param client the {@code Client}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @return the location {@code URI} for the created {@code Client}
	 * @throws RestClientException if request fails
	 */
	public URI createClient(Client client, RequestOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.clients(config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Client> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(client);
		ResponseEntity<Void> result = exchange(request, Void.class, context);
		return result.getHeaders().getLocation();
	}
	
	/**
	 * Update existing {@code Client}
	 * 
	 * <p><b>Required Security Credentials</b>: Admin (global role ADMIN).
	 * 
	 * @param client the {@code Client}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void updateClient(Client client, RequestOptions options, SsoClientContext context) {
		URI uri = makeURI(SsoEndpoints.client(client.getId(), config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
		RequestEntity<Client> request = RequestEntity.put(uri).accept(MediaType.APPLICATION_JSON).body(client);
		exchange(request, Client.class, context);
	}
	
	/**
	 * Delete existing {@code Client}
	 * 
	 * <p><b>Required Security Credentials</b>: Admin (global role ADMIN).
	 * 
	 * @param clientId the {@code Client}
	 * @param options optional {@code RequestOptions}
	 * @param context optional {@code SsoClientContext}
	 * @throws RestClientException if request fails
	 */
	public void deleteClient(String clientId, RequestOptions options, SsoClientContext context) {
		clientId = encodeId(clientId);
		URI uri = makeURI(SsoEndpoints.client(clientId, config, isAdminRequest(options, context)));
		uri = processURI(uri, options);
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
		OAuth2RestTemplate restTemplate = getRequiredRestTemplate(context);
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
	 * Get the {@code OAuth2RestTemplate} to use to perform a request.
	 * 
	 * If the context is not null, returns the {@code OAuth2RestTemplate} specified by the context (if any).
	 * Otherwise, return the configured {@code OAuth2RestTemplate} in property {@link #restTemplate}.
	 * If property {@link web} is true, check if current thread is bound to a web request with a session-scope. If not, fallback
	 * to client credential {@code OAuth2RestTemplate} in property {@link #restTemplate2} or create one if needed.
	 * 
	 * @param context optional {@code SsoClientContext}
	 * @return the {@code OAuth2RestTemplate}
	 */
	protected OAuth2RestTemplate getRequiredRestTemplate(SsoClientContext context) {
		OAuth2RestTemplate restTemplate = this.restTemplate;
		if (context!=null && context.getRestTemplate()!=null) {
			restTemplate = context.getRestTemplate();
		} else {
			if (WebUtil.getHttpServletRequest()==null && web) {
				if (this.restTemplate0==null) {
					this.restTemplate0 = makeClientOAuth2RestTemplate();
				}
				restTemplate = this.restTemplate0;
			}			
		}
		return restTemplate;
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
		ClientHttpRequestFactory clientHttpRequestFactory = config.getConnection().makeClientHttpRequestFactory();
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
		ClientHttpRequestFactory clientHttpRequestFactory = config.getConnection().makeClientHttpRequestFactory();
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
	
	/**
	 * Factory method for a {@code ClientCredentialsResourceDetails} using default setting except client credentials.
	 * 
	 * @param clientId the clientId
	 * @param clientSecret the client secret
	 * @return the {@code ClientCredentialsResourceDetails}
	 */
	public ClientCredentialsResourceDetails makeClientCredentialsResourceDetails(String clientId, String clientSecret) {
		return makeClientCredentialsResourceDetails(clientId, clientSecret, config);
	}

	/**
	 * Factory method for a {@code ClientCredentialsResourceDetails} using the default configuration.
	 * 
	 * @return the {@code ClientCredentialsResourceDetails}
	 */
	public ClientCredentialsResourceDetails makeClientCredentialsResourceDetails() {
		return makeClientCredentialsResourceDetails(config);
	}

	/**
	 * Static utility factory method for a {@code ClientCredentialsResourceDetails}.
	 * 
	 * @param config the {@code SsoClientConfiguration}
	 * @return the {@code ClientCredentialsResourceDetails}
	 */
	public static ClientCredentialsResourceDetails makeClientCredentialsResourceDetails(SsoClientConfiguration config) {
		return makeClientCredentialsResourceDetails(config.getClientId(), config.getClientSecret(), config);
	}


	/**
	 * Static utility factory method for a {@code ClientCredentialsResourceDetails}.
	 * 
	 * @param clientId the clientId
	 * @param clientSecret the client secret
	 * @param config the {@code SsoClientConfiguration}
	 * @return the {@code ClientCredentialsResourceDetails}
	 */
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
	
	/**
	 * Get or request new {@code OAuth2AccessToken} for the session user.
	 * 
	 * @return the {@code OAuth2AccessToken}
	 */
	public OAuth2AccessToken setupToken() {
		return setupToken(false);
	}

	/**
	 * Get or request new {@code OAuth2AccessToken} for the session user.
	 * 
	 * @param force true if not checking if token is already available locally
	 * @return the {@code OAuth2AccessToken}
	 */
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

	/**
	 * Get {@code OAuth2AccessToken} for specified user using the session-scoped {@code OAuth2ClientContext}.
	 * 
	 * @param username the username
	 * @param password the user password
	 * @return the {@code OAuth2AccessToken}
	 */
	public OAuth2AccessToken getToken(String username, String password) {
		return getToken(username, password, oauth2ClientContext);
	}

	/**
	 * Get {@code OAuth2AccessToken} for specified user using specified {@code OAuth2ClientContext}.
	 * 
	 * @param username the username
	 * @param password the user password
	 * @param oauth2ClientContext the {@code OAuth2ClientContext}
	 * @return the {@code OAuth2AccessToken}
	 */
	public OAuth2AccessToken getToken(String username, String password, OAuth2ClientContext oauth2ClientContext) {
		ResourceOwnerPasswordResourceDetails resource = makeResourceOwnerPasswordResourceDetails(username, password);
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource, oauth2ClientContext);
		OAuth2AccessToken token = template.getAccessToken();
		return token;
	}


	public OAuth2AccessToken getSessionToken() {
		return oauth2ClientContext.getAccessToken();
	}
	
	//
	// Static Token utils
	//

	/**
	 * Static utility to get OAuth2 Token value from an {@code OAuth2Authentication}.
	 * 
	 * @param authentication the {@code Authentication}
	 * @return the token value
	 */
	public static OAuth2AccessToken getToken(Authentication authentication) {
		String tokenValue = getTokenValue(authentication);
		if (tokenValue == null) {
			return null;
		}
		return new DefaultOAuth2AccessToken(tokenValue);
	}


	/**
	 * Static utility to get OAuth2 Token for the {@code Principal}.
	 * 
	 * @param principal the {@code Principal}
	 * @return the token value
	 */
	public static OAuth2AccessToken getToken(Principal principal) {
		String tokenValue = getTokenValue(principal);
		if (tokenValue == null) {
			return null;
		}
		return new DefaultOAuth2AccessToken(tokenValue);
	}

	/**
	 * Static utility to get OAuth2 Token value for the {@code Principal}.
	 * 
	 * @return the token value
	 */
	public static OAuth2AccessToken getToken() {
		return getToken(SecurityUtil.getAuthentication());
	}


	/**
	 * Static utility to get OAuth2 Token value for the {@code Principal} as setup in the {@code SecurityContext}.
	 * 
	 * @return the token value
	 */
	public static String getTokenValue() {
		return getTokenValue(SecurityUtil.getAuthentication());
	}

	/**
	 * Static utility to get OAuth2 Token value for the {@code Principal}.
	 * 
	 * @param principal the {@code Principal}
	 * @return the token value
	 */
	public static String getTokenValue(Principal principal) {
		if (principal == null) {
			return null;
		}
		if (!(principal instanceof Authentication)) {
			return null;
		}
		return getTokenValue((Authentication) principal);
	}

	/**
	 * Static utility to get OAuth2 Token value from an {@code OAuth2Authentication}.
	 * 
	 * @param authentication the {@code Authentication}
	 * @return the token value
	 */
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

	/**
	 * Static utility to get type of OAuth2 Token for the {@code Principal}.

	 * @param principal the {@code Principal}
	 * @return the token type
	 */
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

	/**
	 * Static utility to get type of OAuth2 Token session Id.

	 * @param principal the {@code Principal}
	 * @return the session ID
	 */
	public static String getSessionId(Principal principal) {
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
	
	/**
	 * Static utility factory method to create an instance of {@code SsoClient} with client credentials.
	 * 
	 * @param config the {@code SsoClientConfiguration} with server URL, client credentials and other properties
	 * @return the {@code SsoClient}
	 */
	public static SsoClient makeSsoClient(SsoClientConfiguration config) {
		OAuth2ClientContext context = new DefaultOAuth2ClientContext();
		OAuth2ProtectedResourceDetails resource = SsoClient.makeClientCredentialsResourceDetails(config);
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource, context);
		SsoClient ssoClient = new SsoClient(template, config);
		return ssoClient;
	}

	/**
	 * Static utility factory method to create an instance of {@code SsoClient} with user credentials.
	 * 
	 * @param username the username
	 * @param password the user password
	 * @param config the {@code SsoClientConfiguration} with server URL, client credentials and other properties
	 * @return the {@code SsoClient}
	 */
	public static SsoClient makeSsoClient(String username, String password, SsoClientConfiguration config) {
		OAuth2ClientContext context = new DefaultOAuth2ClientContext();
		OAuth2ProtectedResourceDetails resource = SsoClient.makeResourceOwnerPasswordResourceDetails(username, password, config);
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource, context);
		SsoClient ssoClient = new SsoClient(template, config);
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
	public void doLogout(Authentication authentication) {
		if (authentication == null) {
			return;
		}
		Object details = authentication.getDetails();
		if (details.getClass().isAssignableFrom(OAuth2AuthenticationDetails.class)) {
			String accessToken = ((OAuth2AuthenticationDetails) details).getTokenValue();
			SsoClientConfiguration config = this.config;
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
	
	//
	// Other
	//

	/**
	 * Process URI by adding parameters from properties of specified objectes.
	 * 
	 * @param uri the {@code URI}
	 * @param objs a variadic array of objects
	 * @return the processed {@code URI}
	 */
	public static URI processURI(URI uri, Object... objs) {
		if (objs!=null && objs.length>0) {
			Map<String, String> params = new LinkedHashMap<>();
			for (Object obj: objs) {
				if (obj==null) {
					continue;
				}
				if (obj instanceof Pageable) {
					obj = new PageOptions((Pageable)obj);
				}
				params.putAll(MappingUtils.toMapFormatted(obj));
			}
			uri = appendQueryParameters(uri, params);			
		}
		return uri;
	}
	
	
	/**
	 * Check if request is for admin endpoint.
	 * 
	 * @param options optional {@code RequestOptions}
	 * @param context options {@code SsoClientContext}
	 * @return true if reques is for an admin endpoint, false otherwise
	 */
	public static boolean isAdminRequest(RequestOptions options, ClientContext context) {
		if (options!=null && options.getAdmin()!=null) {
			return Boolean.TRUE.equals(options.getAdmin());
		}
		if (context!=null && context.isAdmin()) {
			return true;
		}
		return false;
	}

}
