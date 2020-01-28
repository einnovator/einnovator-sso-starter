/**
 * 
 */
package org.einnovator.sso.client.config;

import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class CustomAccessTokenConverter extends JwtAccessTokenConverter {

	JwtAccessTokenConverter converter;

	public CustomAccessTokenConverter(JwtAccessTokenConverter converter) {
		this.converter = converter;
	}
	
	@Override
	protected Map<String, Object> decode(String token) {
		System.out.println(this + "decode:" +  token);
		Map<String, Object> map = super.decode(token);		
		System.out.println(this + "decode:->" +  map);
		return map;
	}
	
	@Override
	public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		System.out.println(this + "convertAccessToken:" +  token + " " + authentication);
		Map<String, ?> map = converter.convertAccessToken(token, authentication);
		System.out.println(this + "convertAccessToken:->" + map);
		return map;
	}

	@Override
	public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
		System.out.println(this + "extractAccessToken:" +  value + " " + map);
		OAuth2AccessToken token = converter.extractAccessToken(value, map);
		System.out.println(this + "extractAccessToken:->" + token);
		return token;
	}

	@Override
	public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
		System.out.println(this + "extractAuthentication:" +  map);
		OAuth2Authentication auth = converter.extractAuthentication(map);
		System.out.println(this + "extractAuthentication:->" + auth);
		return auth;
	}

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		System.out.println(this + "enhance:" +  accessToken + " " + authentication);
		OAuth2AccessToken token = converter.enhance(accessToken, authentication);
		System.out.println(this + "enhance:->" +  token);
		return token;
	}
	
}