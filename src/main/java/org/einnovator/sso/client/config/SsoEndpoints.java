package org.einnovator.sso.client.config;


/**
 * SSO API endpoints.
 * 
 */
public class SsoEndpoints {

	public static String register(SsoClientConfiguration config) {
		return api(config, false) + "/register";
	}

	public static String user(String id, SsoClientConfiguration config, boolean admin) {
		return users(config, admin) + "/" + id;
	}

	public static String users(SsoClientConfiguration config, boolean admin) {
		return api(config, admin) + "/user";
	}

	public static String usersFlux(SsoClientConfiguration config, boolean admin) {
		return api(config, admin) + "/r/user";
	}

	public static String group(String groupId, SsoClientConfiguration config, boolean admin) {
		return groups(config, admin) + "/" + groupId;
	}

	private static String groupFlux(String groupId, SsoClientConfiguration config, boolean admin) {
		return groupsFlux(config, admin) + "/" + groupId;
	}

	public static String groups(SsoClientConfiguration config, boolean admin) {
		return api(config, admin) + "/group";
	}

	public static String groupsFlux(SsoClientConfiguration config, boolean admin) {
		return api(config, admin) + "/r/group";
	}

	public static String groupMembers(String groupId, SsoClientConfiguration config, boolean admin) {
		return group(groupId, config, admin) + "/member";
	}

	public static String groupMembersFlux(String groupId, SsoClientConfiguration config, boolean admin) {
		return groupFlux(groupId, config, admin) + "/member";
	}

	public static String countMembers(String groupId, SsoClientConfiguration config, boolean admin) {
		return groupMembers(groupId, config, admin) + "?count=true";
	}

	public static String countGroups(SsoClientConfiguration config, boolean admin) {
		return groups(config, admin) + "?count=true";
	}

	public static String member(String groupId, String userId, SsoClientConfiguration config, boolean admin) {
		return groupMembers(groupId, config, admin) + "/" + userId;
	}

	public static String invite(SsoClientConfiguration config, boolean admin) {
		return api(config, admin) + "/invite";
	}

	public static String invitations(SsoClientConfiguration config, boolean admin) {
		return api(config, admin) + "/invitation";
	}

	public static String invitationsFlux(SsoClientConfiguration config, boolean admin) {
		return api(config, admin) + "/r/invitation";
	}

	public static String invitation(String invitationId, SsoClientConfiguration config, boolean admin) {
		return api(config, admin) + "/invitation/" + invitationId;
	}

	public static String invitationStats(SsoClientConfiguration config, boolean admin) {
		return api(config, admin) + "/invitation/stats";
	}

	public static String invitationToken(String invitationId, SsoClientConfiguration config, boolean admin) {
		return api(config, admin) + "/invitation/" + invitationId + "/token";
	}

	public static String role(String roleId, SsoClientConfiguration config, boolean admin) {
		return roles(config, admin) + "/" + roleId;
	}

	private static String roleFlux(String roleId, SsoClientConfiguration config, boolean admin) {
		return rolesFlux(config, admin) + "/" + roleId;
	}

	public static String roles(SsoClientConfiguration config, boolean admin) {
		return api(config, admin) + "/role";
	}

	public static String rolesFlux(SsoClientConfiguration config, boolean admin) {
		return api(config, admin) + "/r/role";
	}

	public static String roleMembers(String roleId, SsoClientConfiguration config, boolean admin) {
		return role(roleId, config, admin) + "/member";
	}
	public static String roleMembersFlux(String roleId, SsoClientConfiguration config, boolean admin) {
		return roleFlux(roleId, config, admin) + "/member";
	}

	public static String countRoleMembers(String roleId, SsoClientConfiguration config, boolean admin) {
		return roleMembers(roleId, config, admin) + "?count=true";
	}

	public static String getLoginEndpoint(SsoClientConfiguration config) {
		return config.getServer() + "/login";
	}

	public static String getLogoutEndpoint(SsoClientConfiguration config) {
		return config.getServer() + "/logout";
	}
	
	public static String password(SsoClientConfiguration config, boolean admin) {
		return users(config, admin) + "/password";

	}

	public static String client(String id, SsoClientConfiguration config, boolean admin) {
		return clients(config, admin) + "/" + id;
	}

	public static String clients(SsoClientConfiguration config, boolean admin) {
		return api(config, true) + "/client";
	}
	
	public static String clientsFlux(SsoClientConfiguration config, boolean admin) {
		return api(config, true) + "/r/client";
	}
	
	public static String api(SsoClientConfiguration config, boolean admin) {
		String s = api(config, admin) + "";
		if (admin) {
			s += "/admin";
		}
		return s;
	}

	public static String getAuthorizationEndpoint(SsoClientConfiguration config) {
		return config.getServer() + getEndpointPrefix(config) + "/authorize";
	}

	public static String getTokenEndpoint(SsoClientConfiguration config) {
		return config.getServer() + getEndpointPrefix(config) + "/token";
	}

	public static String getUserInfoEndpoint(SsoClientConfiguration config) {
		return config.getServer() + getEndpointPrefix(config) + "/userinfo";
	}

	public static String getCheckTokenEndpoint(SsoClientConfiguration config) {
		return config.getServer() + getEndpointPrefix(config) +  "/check_token";
	}

	public static String getKeyEndpoint(SsoClientConfiguration config) {
		return config.getServer() + getEndpointPrefix(config) + "/token_key";
	}

	public static String getTokenRevokeEndpoint(SsoClientConfiguration config) {
		return api(config, false) + "/logout";
	}


	public static String getEndpointPrefix(SsoClientConfiguration config) {
		return "/oauth";
	}


}
