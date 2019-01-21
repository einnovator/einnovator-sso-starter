package org.einnovator.sso.client;

import static org.einnovator.util.UriUtils.appendQueryParameters;
import static org.einnovator.util.UriUtils.makeURI;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.util.PageOptions;
import org.einnovator.util.MappingUtils;
import org.einnovator.util.PageUtil;
import org.einnovator.util.UriUtils;
import org.einnovator.sso.client.config.SsoClientConfiguration;
import org.einnovator.sso.client.config.SsoEndpoints;
import org.einnovator.sso.client.model.Group;
import org.einnovator.sso.client.model.GroupType;
import org.einnovator.sso.client.model.Invitation;
import org.einnovator.sso.client.model.InvitationFilter;
import org.einnovator.sso.client.model.InvitationStats;
import org.einnovator.sso.client.model.Member;
import org.einnovator.sso.client.model.Permission;
import org.einnovator.sso.client.model.Role;
import org.einnovator.sso.client.model.RoleFilter;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.GroupFilter;
import org.einnovator.sso.client.modelx.MemberFilter;
import org.einnovator.sso.client.modelx.UserFilter;
import org.einnovator.sso.client.modelx.UserOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class SsoClient {

	private final Log logger = LogFactory.getLog(getClass());

	private SsoClientConfiguration config;

	private static final String DEFAULT_ENCODING = "UTF-8";

	@Autowired
	@Qualifier("ssoOAuth2RestTemplate")
	private OAuth2RestTemplate restTemplate;

	@Autowired
	private OAuth2ClientContext oauth2Context;

	@Autowired(required = false)
	private ClientHttpRequestFactory ssoClientHttpRequestFactory;

	@Autowired
	public SsoClient(SsoClientConfiguration config) {
		this.config = config;
	}

	public SsoClient(OAuth2RestTemplate restTemplate, SsoClientConfiguration config) {
		this.config = config;
		this.restTemplate = restTemplate;
		this.oauth2Context = restTemplate.getOAuth2ClientContext();
	}

	public SsoClient(SsoClientConfiguration config, ClientHttpRequestFactory clientHttpRequestFactory) {
		this.config = config;
		ssoClientHttpRequestFactory = clientHttpRequestFactory;
	}

	private boolean autoSetupToken = true;
	
	@PostConstruct
	public void init() {
		if (ssoClientHttpRequestFactory==null) {
			ssoClientHttpRequestFactory = config.getConnection().makeClientHttpRequestFactory();
		}
	}
		
	public OAuth2RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(OAuth2RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public OAuth2ClientContext getOauth2Context() {
		return oauth2Context;
	}

	public void setOauth2Context(OAuth2ClientContext oauth2Context) {
		this.oauth2Context = oauth2Context;
	}
	
	
	public boolean isAutoSetupToken() {
		return autoSetupToken;
	}


	public void setAutoSetupToken(boolean autoSetupToken) {
		this.autoSetupToken = autoSetupToken;
	}


	public OAuth2RestTemplate makeOAuth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext oauth2ClientContext) {
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource, oauth2ClientContext);			
		template.setRequestFactory(ssoClientHttpRequestFactory);
		return template;
	}
	
	public OAuth2RestTemplate makeOAuth2RestTemplate(OAuth2ClientContext oauth2ClientContext) {
		ClientCredentialsResourceDetails resource = makeClientCredentialsResourceDetails();
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource, oauth2ClientContext);			
		template.setRequestFactory(ssoClientHttpRequestFactory);
		return template;
	}
	
	public User getUser(String id) {
		return getUser(id, UserOptions.DEFAULT_OPTIONS);
	}
	
	public User getUser(String id, UserOptions options) {
		id = encodeId(id);
		URI uri = makeURI(SsoEndpoints.user(id, config));
		if (options!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			params.putAll(MappingUtils.toMapFormatted(options));
			uri = appendQueryParameters(uri, params);			
		}

		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<User> result = exchange(request, User.class);
		return result.getBody();
	}
	

	public static String encodeId(String userId) {
		if (userId == null) {
			return null;
		}
		userId = userId.replace(".", "@@");
		userId = encode(userId);
		return userId;
	}

	public static String decodeId(String userId) {
		if (userId == null) {
			return null;
		}
		userId = decode(userId);
		userId = userId.replace("@@", ".");
		return userId;
	}

	public static String encode(String path) {
		if (path == null) {
			return null;
		}
		path = UriUtils.encode(path, DEFAULT_ENCODING);
		return path;
	}

	public static String decode(String path) {
		if (path == null) {
			return null;
		}
		path = UriUtils.decode(path, DEFAULT_ENCODING);
		return path;
	}


	public Page<User> listUsers(UserFilter filter, Pageable pageable) {
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
		ResponseEntity<Page> result = exchange(request, Page.class);
		return PageUtil.create(result.getBody(),  User.class);
	}

	public URI createUser(User user) {
		URI uri = makeURI(SsoEndpoints.users(config));
		RequestEntity<User> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(user);
		
		ResponseEntity<Void> result = exchange(request, Void.class);
		return result.getHeaders().getLocation();
	}
	
	public void updateUser(User user) {
		URI uri = makeURI(SsoEndpoints.user(user.getId(), config));
		RequestEntity<User> request = RequestEntity.put(uri).accept(MediaType.APPLICATION_JSON).body(user);
		
		exchange(request, User.class);
	}
	
	public void deleteUser(String userId) {
		userId = encodeId(userId);
		URI uri = makeURI(SsoEndpoints.user(userId, config));
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		
		exchange(request, Void.class);
	}
	
	public URI createGroup(Group group) {
		URI uri = makeURI(SsoEndpoints.groups(config));
		RequestEntity<Group> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(group);
		
		ResponseEntity<Group> result = exchange(request, Group.class);
		return result.getHeaders().getLocation();
	}

	public Group getGroup(String groupId) {
		return getGroup(groupId, null);
	}

	public Group getGroup(String groupId, GroupFilter filter) {
		groupId = encode(groupId);
		URI uri = makeURI(SsoEndpoints.group(groupId, config));
		
		if (filter!=null) {
			uri = appendQueryParameters(uri, filter);
		}

		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Group> result = exchange(request, Group.class);
		return result.getBody();
	}
	
	public void updateGroup(Group group) {
		URI uri = makeURI(SsoEndpoints.group(encode(group.getId()), config));
		RequestEntity<Group> request = RequestEntity.put(uri).accept(MediaType.APPLICATION_JSON).body(group);		
		exchange(request, Group.class);
	}
	
	public Page<Group> listGroups(Pageable pageable) {
		return listGroups(pageable, null);
	}
	
	public Page<Group> listGroups(Pageable pageable, GroupFilter filter) {
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
		ResponseEntity<Page> result = exchange(request, Page.class);
		return PageUtil.create(result.getBody(),  Group.class);
	}
	
	public void deleteGroup(String groupId) {
		groupId = encode(groupId);
		URI uri = makeURI(SsoEndpoints.group(groupId, config));
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class);
	}
	
	public Page<Member> listGroupMembers(String groupId, MemberFilter filter, Pageable pageable) {
		groupId = encode(groupId);
		URI uri = makeURI(SsoEndpoints.groupMembers(groupId, config));
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
		ResponseEntity<Page> result = exchange(request, Page.class);
		return PageUtil.create(result.getBody(),  Member.class);
	}

	public Integer countGroupMembers(String groupId, MemberFilter filter) {
		groupId = encode(groupId);
		URI uri = makeURI(SsoEndpoints.countMembers(groupId, config));
		if (filter!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			if (filter!=null) {
				params.putAll(MappingUtils.toMapFormatted(filter));
			}
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Integer> result = exchange(request, Integer.class);
		return result.getBody();
	}

	public Member getGroupMember(String groupId, String userId) {
		groupId = encode(groupId);
		userId = encodeId(userId);
		URI uri = makeURI(SsoEndpoints.member(groupId, userId, config));
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Member> result = exchange(request, Member.class);
		try {
			return result.getBody();			
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode()==HttpStatus.NOT_FOUND) {
				return null;
			}
			throw e;
		}
	}

	public void addToGroup(String userId, String groupId) {
		groupId = encode(groupId);
		userId = encodeId(userId);
		URI uri = makeURI(SsoEndpoints.groupMembers(groupId, config) + "?username=" + userId);
		RequestEntity<Void> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).build();		
		exchange(request, Void.class);
	}
	
	public void removeFromGroup(String userId, String groupId) {
		groupId = encode(groupId);
		userId = encodeId(userId);
		URI uri = makeURI(SsoEndpoints.groupMembers(groupId, config) + "?username=" + userId);
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class);
	}
	
	public Page<Group> listGroupsForUser(String userId, GroupType type, Pageable pageable) {
		GroupFilter filter = new GroupFilter();
		filter.setOwner(userId);
		filter.setType(type);
		return listGroups(pageable, filter);
	}

	public Page<Group> listSubGroups(String groupId, GroupType type, boolean direct, Pageable pageable) {
		groupId = encode(groupId);
		GroupFilter filter = new GroupFilter();
		if (direct) {
			filter.setParent(groupId);			
		} else {
			filter.setRoot(groupId);
		}
		filter.setType(type);
		return listGroups(pageable, filter);
	}

	public Integer countGroups(GroupFilter filter) {
		URI uri = makeURI(SsoEndpoints.countGroups(config));
		if (filter!=null) {
			uri = appendQueryParameters(uri, filter);			
		}
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Integer> result = exchange(request, Integer.class);
		return result.getBody();
	}
	
	public Integer countSubGroups(String groupId, GroupType type, boolean direct) {
		GroupFilter filter = new GroupFilter();
		if (direct) {
			filter.setParent(groupId);			
		} else {
			filter.setRoot(groupId);
		}
		filter.setType(type);
		return countGroups(filter);
	}
		
	public static User makeUser(Principal principal) {
		return new User(getProfile(principal));
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getProfile(Principal principal) {
		User user = getUser(principal);
		if (user==null) {
			return null;
		}
		return MappingUtils.convert(user, LinkedHashMap.class);			
	}

	public User getUser(Principal principal, boolean invalid) {
		if (invalid) {
			try {
				User user = getUser(principal.getName());				
				return user;
			} catch (RuntimeException e) {
				logger.error("getUser: " + principal.getName() + " " + e);
			}
		}
		return getUser(principal);
	}

	public static User getUser(Principal principal) {
		if (principal==null) {
			return null;
		}
		if (!(principal instanceof OAuth2Authentication)) {
			return new User(principal.getName());
		}
		OAuth2Authentication oauth2 = (OAuth2Authentication)principal;
		Authentication auth = oauth2.getUserAuthentication();
		@SuppressWarnings("unchecked")
		Map<String, Object> details = auth!=null ? (Map<String, Object>) auth.getDetails() : null;
		if (details==null) {
			return new User(principal.getName());
		}
		Object profile = details.get("profile");
		if (profile==null) {
			return new User(principal.getName());
		}
		try {
			User user = MappingUtils.fromJson(profile.toString(), User.class);	
			return user;		
		} catch (RuntimeException e) {
			System.err.println(profile.toString() + " : " + e);
			return new User(principal.getName());
		}
	}
	
	public URI invite(Invitation invitation, Boolean sendMail, String redirectUri) {
		URI uri = makeURI(SsoEndpoints.invite(config));
		if (sendMail!=null || redirectUri!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			if (sendMail!=null) {
				params.put("sendMail", sendMail!=null ? Boolean.toString(sendMail) : null);				
			}
			if (redirectUri!=null) {
				params.put("redirectUri", redirectUri);				
			}
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Invitation> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(invitation);		
		ResponseEntity<Void> result = exchange(request, Void.class);
		return result.getHeaders().getLocation();
	}
	
	public Page<Invitation> listInvitations(InvitationFilter filter, Pageable pageable) {
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
		ResponseEntity<Page> result = exchange(request, Page.class);
		return PageUtil.create(result.getBody(),  Invitation.class);
	}

	
	public Invitation getInvitation(String id) {
		URI uri = makeURI(SsoEndpoints.invitation(id, config));
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Invitation> result = exchange(request, Invitation.class);
		return result.getBody();
	}

	public void updateInvitation(Invitation invitation) {
		URI uri = makeURI(SsoEndpoints.invitation(invitation.getUuid(), config));
		RequestEntity<Invitation> request = RequestEntity.put(uri).accept(MediaType.APPLICATION_JSON).body(invitation);

		exchange(request, Invitation.class);
	}

	@SuppressWarnings("unchecked")
	public void updateInvitation(Invitation invitation, Boolean publish) {
		URI uri = makeURI(SsoEndpoints.invitation(invitation.getUuid(), config));
		if (publish != null ) {
			Map<String, Object> params = new LinkedHashMap<>();
			if (publish != null) {
				params.put("publish", publish);
			}
			uri = appendQueryParameters(uri, MappingUtils.convert(params, LinkedHashMap.class));
		}
		RequestEntity<Invitation> request = RequestEntity.put(uri).accept(MediaType.APPLICATION_JSON).body(invitation);
		exchange(request, Invitation.class);
	}

	public InvitationStats getInvitationStats() {
		URI uri = makeURI(SsoEndpoints.invitationStats(config));
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<InvitationStats> result = exchange(request, InvitationStats.class);
		return result.getBody();
	}

	public URI getInvitationToken(String id, Boolean sendMail) {
		URI uri = makeURI(SsoEndpoints.invitationToken(id, config));
		if (sendMail!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			if (sendMail!=null) {
				params.put("sendMail", sendMail!=null ? Boolean.toString(sendMail) : null);				
			}
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Void> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).build();		
		ResponseEntity<Void> result = exchange(request, Void.class);
		return result.getHeaders().getLocation();
	}

	public URI createPermission(Permission permission) {
		URI uri = makeURI(SsoEndpoints.permissions(config));
		RequestEntity<Permission> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(permission);
		
		ResponseEntity<Permission> result = exchange(request, Permission.class);
		return result.getHeaders().getLocation();
	}
	
	public Permission getPermission(String id) {
		URI uri = makeURI(SsoEndpoints.permission(id, config));
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Permission> result = exchange(request, Permission.class);
		return result.getBody();
	}

	
	public Page<Permission> listPermissions(Pageable pageable) {
		URI uri = makeURI(SsoEndpoints.permissions(config));
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		@SuppressWarnings("rawtypes")
		ResponseEntity<Page> result = exchange(request, Page.class);
		return PageUtil.create(result.getBody(),  Permission.class);
	}
	
	public void deletePermission(String id) {
		URI uri = makeURI(SsoEndpoints.permission(id, config));
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		
		exchange(request, Void.class);
	}

	public Page<User> listPermissionMembers(String permissionId, Pageable pageable, UserFilter filter) {
		return listPermissionMembers(permissionId, null, pageable, filter);
	}

	public Page<User> listPermissionMembers(String permissionId, String groupId, Pageable pageable, UserFilter filter) {
		URI uri = makeURI(SsoEndpoints.permissionMembers(permissionId, config));
		if (filter != null || pageable != null || groupId!=null) {

			Map<String, String> params = new LinkedHashMap<>();
			if (filter != null) {
				params.putAll(MappingUtils.toMapFormatted(filter));
			}
			if (pageable != null) {
				params.putAll(MappingUtils.toMapFormatted(new PageOptions(pageable)));
			}
			if (groupId!=null) {
				params.put("groupId", groupId);
			}
			uri = appendQueryParameters(uri, params);
		}
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		@SuppressWarnings("rawtypes")
		ResponseEntity<Page> result = exchange(request, Page.class);
		return PageUtil.create(result.getBody(), User.class);
	}

	public URI createRole(Role role) {
		URI uri = makeURI(SsoEndpoints.roles(config));
		RequestEntity<Role> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(role);
		
		ResponseEntity<Role> result = exchange(request, Role.class);
		return result.getHeaders().getLocation();
	}
	
	public Role getRole(String roleId) {
		URI uri = makeURI(SsoEndpoints.role(roleId, config));
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Role> result = exchange(request, Role.class);
		return result.getBody();
	}
	
	public void updateRole(Role role) {
		URI uri = makeURI(SsoEndpoints.role(role.getId(), config));
		RequestEntity<Role> request = RequestEntity.put(uri).accept(MediaType.APPLICATION_JSON).body(role);
		
		exchange(request, Role.class);
	}
	
	public Page<Role> listRoles(RoleFilter filter, Pageable pageable) {
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
		ResponseEntity<Page> result = exchange(request, Page.class);
		return PageUtil.create(result.getBody(),  Role.class);
	}
	
	public void deleteRole(String roleId) {
		URI uri = makeURI(SsoEndpoints.role(roleId, config));
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		
		exchange(request, Void.class);
	}
	
	public Page<User> listRoleMembers(String roleId, Pageable pageable, UserFilter filter) {
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
		ResponseEntity<Page> result = exchange(request, Page.class);
		return PageUtil.create(result.getBody(),  User.class);
	}

	public Integer countRoleMembers(String roleId, UserFilter filter) {
		URI uri = makeURI(SsoEndpoints.countRoleMembers(roleId, config));
		if (filter!=null) {
			Map<String, String> params = new LinkedHashMap<>();
			if (filter!=null) {
				params.putAll(MappingUtils.toMapFormatted(filter));
			}
			uri = appendQueryParameters(uri, params);			
		}
		RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Integer> result = exchange(request, Integer.class);
		return result.getBody();
	}

	public void assignRole(String userId, String roleId) {
		URI uri = makeURI(SsoEndpoints.roleMembers(roleId, config) + "?username=" + userId);
		userId = encodeId(userId);
		RequestEntity<Void> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class);
	}
	
	public void removeFromRole(String userId, String roleId) {
		userId = encodeId(userId);
		URI uri = makeURI(SsoEndpoints.roleMembers(roleId, config) + "?username=" + userId);
		RequestEntity<Void> request = RequestEntity.delete(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class);
	}
	
	public Page<Role> listRolesForUser(String userId, Pageable pageable) {
		RoleFilter filter = new RoleFilter();
		userId = encodeId(userId);
		filter.setUser(userId);
		return listRoles(filter, pageable);
	}

	public Page<Role> listRolesForUserInGroup(String userId, String groupId, Pageable pageable) {
		RoleFilter filter = new RoleFilter();
		userId = encodeId(userId);
		filter.setUser(userId);
		filter.setOrgId(groupId);
		return listRoles(filter, pageable);
	}

	public List<Role> listRolesForUser(String userId) {
		Page<Role> page = listRolesForUser(userId, (Pageable)null);
		return page!=null ? page.getContent() : null;
	}

	public List<Role> listRolesForUserInGroup(String userId, String groupId) {
		Page<Role> page = listRolesForUserInGroup(userId, groupId, (Pageable)null);
		return page!=null ? page.getContent() : null;
	}

	public void changePassword(String password) {
		URI uri = makeURI(SsoEndpoints.password(config) + "?password=" + password);
		RequestEntity<Void> request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).build();
		exchange(request, Void.class);
	}

	protected <T> ResponseEntity<T> exchange(RequestEntity<?> request, Class<T> responseType)
			throws RestClientException {
		if (autoSetupToken) {
			setupToken();
		}
		return restTemplate.exchange(request, responseType);
	}

	public OAuth2AccessToken setupToken() {
		return setupToken(false);
	}

	public OAuth2AccessToken setupToken(boolean force) {
		OAuth2AccessToken token = null;

		if (!force) {
			token = oauth2Context.getAccessToken();
			if (token != null) {
				return token;
			}
		}

		token = getToken();
		if (token == null) {
			logger.warn("setupToken: No token found");
			return null;
		}
		oauth2Context.setAccessToken(token);
		return token;
	}

	public OAuth2AccessToken setupClientToken() {
		return setupClientToken(config.getClientId(), config.getClientSecret());
	}

	public OAuth2AccessToken setupClientToken(String clientId, String clientSecret) {
		return setupClientToken(clientId, clientSecret, oauth2Context);
	}

	public OAuth2AccessToken setupClientToken(OAuth2ClientContext oauth2Context) {
		return setupClientToken(config.getClientId(), config.getClientSecret(), oauth2Context);
	}

	public OAuth2AccessToken setupClientToken(String clientId, String clientSecret, OAuth2ClientContext oauth2Context) {
		return setupClientToken(clientId, clientSecret, oauth2Context, false, false);
	}

	public OAuth2AccessToken setupClientToken(boolean force, boolean cached) {
		return setupClientToken(config.getClientId(), config.getClientSecret(), force, cached);
	}

	public OAuth2AccessToken setupClientToken(String clientId, String clientSecret, boolean force, boolean cached) {
		return setupClientToken(clientId, clientSecret, oauth2Context, force, cached);
	}

	public OAuth2AccessToken setupClientToken(OAuth2ClientContext oauth2Context, boolean force, boolean cached) {
		return setupClientToken(config.getClientId(), config.getClientSecret(), oauth2Context, force, cached);
	}

	public OAuth2AccessToken setupClientToken(String clientId, String clientSecret, OAuth2ClientContext oauth2Context,
			boolean force, boolean cached) {
		OAuth2AccessToken token = null;
		if (!force) {
			token = oauth2Context.getAccessToken();
			if (token != null) {
				return token;
			}
		}

		token = getClientToken(clientId, clientSecret, oauth2Context);
		if (token == null) {
			logger.warn("setupToken: No token found");
			return null;
		}
		oauth2Context.setAccessToken(token);
		return token;
	}

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

	public ResourceOwnerPasswordResourceDetails makeResourceOwnerPasswordResourceDetails(String username,
			String password) {
		return makeResourceOwnerPasswordResourceDetails(username, password, config);
	}

	public OAuth2AccessToken getToken(String username, String password) {
		return getToken(username, password, oauth2Context);
	}

	public OAuth2AccessToken getToken(String username, String password, OAuth2ClientContext oauth2Context) {
		ResourceOwnerPasswordResourceDetails resource = makeResourceOwnerPasswordResourceDetails(username, password);
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource, oauth2Context);
		OAuth2AccessToken token = template.getAccessToken();
		return token;
	}

	public OAuth2AccessToken getClientToken(String clientId, String clientSecret) {
		return getClientToken(clientId, clientSecret, oauth2Context);
	}

	public OAuth2AccessToken getClientToken(String clientId, String clientSecret, OAuth2ClientContext oauth2Context) {
		ClientCredentialsResourceDetails resource = makeClientCredentialsResourceDetails(clientId, clientSecret);
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource, oauth2Context);
		OAuth2AccessToken token = template.getAccessToken();
		return token;
	}

	public OAuth2AccessToken getClientToken(OAuth2ClientContext oauth2Context) {
		return getClientToken(config.getClientId(), config.getClientSecret(), oauth2Context);
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
		return getToken(getAuthentication());
	}

	public OAuth2AccessToken getSessionToken() {
		return oauth2Context.getAccessToken();
	}

	public static String getTokenValue() {
		return getTokenValue(getAuthentication());
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

	public static String getUsername(Principal principal) {
		if (principal == null) {
			return null;
		}
		Map<String, Object> profile = SsoClient.getProfile(principal);
		if (profile == null) {
			return principal.getName();
		}
		if (StringUtils.hasText((String) profile.get("username"))) {
			return (String) profile.get("username");
		}
		return principal.getName();
	}

	public static Principal getPrincipal() {
		Authentication authentication = getAuthentication();
		if (authentication != null) {
			if (authentication instanceof Principal) {
				return authentication;
			}
			if (authentication.getPrincipal() instanceof Principal) {
				return (Principal) authentication.getPrincipal();
			}
		}
		return null;
	}

	public static Collection<? extends GrantedAuthority> getAuthorities() {
		Authentication authentication = getAuthentication();
		if (authentication != null) {
			return authentication.getAuthorities();
		}
		return null;
	}

	public static Collection<? extends GrantedAuthority> getAuthorities(Principal principal) {
		if (principal == null) {
			principal = getPrincipal();
		}
		if (principal instanceof OAuth2Authentication) {
			OAuth2Authentication oauth2 = (OAuth2Authentication) principal;
			// Authentication auth = oauth2.getUserAuthentication();
			if (oauth2.getAuthorities() != null) {
				return oauth2.getAuthorities();
			}
		}
		return null;
	}

	public static Authentication getAuthentication() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			return context.getAuthentication();
		}
		return null;
	}

	public void doLogout() {
		@SuppressWarnings("rawtypes")
		RequestEntity request2 = RequestEntity.post(makeURI(SsoEndpoints.getTokenRevokeEndpoint(config)))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).build();
		try {
			ResponseEntity<Void> response2 = exchange(request2, Void.class);
			logger.info("logout:" + response2);
		} catch (RuntimeException e) {
			logger.error("logout:" + e);
		}
	}

	public void doLogout(Authentication authentication) {
		if (authentication == null) {
			return;
		}
		Object details = authentication.getDetails();
		if (details.getClass().isAssignableFrom(OAuth2AuthenticationDetails.class)) {
			String accessToken = ((OAuth2AuthenticationDetails) details).getTokenValue();
			@SuppressWarnings("rawtypes")
			RequestEntity request2 = RequestEntity.post(makeURI(SsoEndpoints.getTokenRevokeEndpoint(config)))
					.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", "Bearer " + accessToken).build();
			try {
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<Void> response2 = restTemplate.exchange(request2, Void.class);
				logger.info("logout:" + response2);
			} catch (RuntimeException e) {
				logger.error("logout:" + e);
			}
		}
	}

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

}
