/**
 * 
 */
package org.einnovator.sso.client.config;

import java.util.Collection;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

public class CustomJwtTokenStore implements TokenStore {
	JwtTokenStore store;
	

	public CustomJwtTokenStore(JwtTokenStore store) {
		this.store = store;
	}

	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		System.out.println("readAuthentication:" + token);
		OAuth2Authentication auth = store.readAuthentication(token);
		System.out.println("readAuthentication:->" + auth);
		return auth;
	}


	public OAuth2Authentication readAuthentication(String token) {
		System.out.println("readAuthentication:" + token);
		OAuth2Authentication auth =  store.readAuthentication(token);
		System.out.println("readAuthentication:->" + token);
		return auth;
	}

	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		System.out.println("storeAccessToken:" + token + " " + authentication);
		store.storeAccessToken(token, authentication);
	}

	public OAuth2AccessToken readAccessToken(String tokenValue) {
		try {
			System.out.println("readAccessToken:" + tokenValue);
			OAuth2AccessToken token =  store.readAccessToken(tokenValue);
			System.out.println("readAccessToken:->" + token);
			return token;				
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void removeAccessToken(OAuth2AccessToken token) {
		System.out.println("removeAccessToken:" + token);
		store.removeAccessToken(token);
	}

	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		System.out.println("storeRefreshToken:" + refreshToken + " " + authentication);
		store.storeRefreshToken(refreshToken, authentication);
	}


	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		System.out.println("readRefreshToken:" + tokenValue);
		OAuth2RefreshToken token =  store.readRefreshToken(tokenValue);
		System.out.println("readRefreshToken:->" + token);
		return token;
	}


	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		System.out.println("readAuthenticationForRefreshToken:" + token);
		OAuth2Authentication auth =  store.readAuthenticationForRefreshToken(token);
		System.out.println("readAuthenticationForRefreshToken:->" + token);
		return auth;
	}


	public void removeRefreshToken(OAuth2RefreshToken token) {
		System.out.println("removeRefreshToken:" + token);
		store.removeRefreshToken(token);
	}


	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		System.out.println("refreshToken:" + refreshToken);
		store.removeAccessTokenUsingRefreshToken(refreshToken);
	}


	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		System.out.println("getAccessToken:" + authentication);
		OAuth2AccessToken token = store.getAccessToken(authentication);
		System.out.println("getAccessToken:->" + token);
		return token;
	}


	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		System.out.println("findTokensByClientIdAndUserName:" + clientId + " " + userName);
		Collection<OAuth2AccessToken> tokens = store.findTokensByClientIdAndUserName(clientId, userName);
		System.out.println("findTokensByClientIdAndUserName:" + tokens);
		return tokens;
	}


	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		System.out.println("findTokensByClientId:" + clientId);
		Collection<OAuth2AccessToken> tokens = store.findTokensByClientId(clientId);
		System.out.println("findTokensByClientId:" + tokens);
		return tokens;
	}

	
}