package org.einnovator.sso.client.config;

public class SsoEndpoints {

	public static String user(String id, SsoClientConfiguration config) {
		return users(config) + "/" + id;
	}

	public static String users(SsoClientConfiguration config) {
		return config.getServer() + "/api/user";
	}
	
	public static String group(String groupId, SsoClientConfiguration config) {
		return groups(config) + "/" + groupId;
	}

	public static String groups(SsoClientConfiguration config) {
		return config.getServer() + "/api/group";
	}
	
	public static String groupMembers(String groupId, SsoClientConfiguration config) {
		return group(groupId, config) + "/member";
	}

	public static String countMembers(String groupId, SsoClientConfiguration config) {
		return groupMembers(groupId, config) + "?count=true";
	}

	public static String countGroups(SsoClientConfiguration config) {
		return groups(config) + "?count=true";
	}

	public static String member(String groupId, String userId, SsoClientConfiguration config) {
		return groupMembers(groupId, config) + "/" + userId;
	}

	public static String invite(SsoClientConfiguration config) {
		return config.getServer() + "/api/invite";
	}

	public static String invitations(SsoClientConfiguration config) {
		return config.getServer() + "/api/invitation";
	}

	public static String invitation(String invitationId, SsoClientConfiguration config) {
		return config.getServer() + "/api/invitation/" + invitationId;
	}

	public static String invitationStats(SsoClientConfiguration config) {
		return config.getServer() + "/api/invitation/stats";
	}

	public static String invitationToken(String invitationId, SsoClientConfiguration config) {
		return config.getServer() + "/api/invitation/" + invitationId + "/token";
	}

	public static String permission(String id, SsoClientConfiguration config) {
		return permissions(config) + "/" + id;
	}

	public static String permissions(SsoClientConfiguration config) {
		return config.getServer() + "/api/permission";
	}
	
	public static String permissionMembers(String id, SsoClientConfiguration config) {
		return permission(id, config) + "/members";
	}


	public static String role(String roleId, SsoClientConfiguration config) {
		return roles(config) + "/" + roleId;
	}

	public static String roles(SsoClientConfiguration config) {
		return config.getServer() + "/api/role";
	}
	
	public static String roleMembers(String roleId, SsoClientConfiguration config) {
		return role(roleId, config) + "/member";
	}

	public static String countRoleMembers(String roleId, SsoClientConfiguration config) {
		return roleMembers(roleId, config) + "?count=true";
	}

	public static String getLoginEndpoint(SsoClientConfiguration config) {
		return config.getServer() + "/gateway/login";
	}

	public static String getLogoutEndpoint(SsoClientConfiguration config) {
		return config.getServer() + "/logout";
	}
	
	public static String getTokenRevokeEndpoint(SsoClientConfiguration config) {
		return config.getServer() + "/api/logout";
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

	public static String getEndpointPrefix(SsoClientConfiguration config) {
		return "/oauth";
	}

	public static String password(SsoClientConfiguration config) {
		return users(config) + "/password";

	}

	
}
