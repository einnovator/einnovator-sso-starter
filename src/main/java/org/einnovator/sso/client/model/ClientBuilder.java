package org.einnovator.sso.client.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.einnovator.util.model.Address;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.Jackson2ArrayOrStringDeserializer;
import org.springframework.security.oauth2.provider.client.JacksonArrayOrStringDeserializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class ClientBuilder {
	
	private String clientId;
	
	private String clientSecret;

	private Set<String> scope = Collections.emptySet();

	private Set<String> resourceIds = Collections.emptySet();

	private Set<String> authorizedGrantTypes = Collections.emptySet();

	private Set<String> registeredRedirectUris;

	private Set<String> autoApproveScopes;

	private List<GrantedAuthority> authorities = Collections.emptyList();

	private Integer accessTokenValiditySeconds;

	private Integer refreshTokenValiditySeconds;

	private Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();

	private String avatar;
	
	private String thumbnail;

	public ClientBuilder() {
	}

	
	
	/**
	 * Set the value of property {@code clientId}.
	 *
	 * @param clientId the clientId to 
	 */
	public ClientBuilder clientId(String clientId) {
		this.clientId = clientId;
		return this;
	}



	/**
	 * Set the value of property {@code clientSecret}.
	 *
	 * @param clientSecret the clientSecret to 
	 */
	public ClientBuilder clientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
		return this;
	}



	/**
	 * Set the value of property {@code scope}.
	 *
	 * @param scope the scope to 
	 */
	public ClientBuilder scope(Set<String> scope) {
		this.scope = scope;
		return this;
	}



	/**
	 * Set the value of property {@code resourceIds}.
	 *
	 * @param resourceIds the resourceIds to 
	 */
	public ClientBuilder resourceIds(Set<String> resourceIds) {
		this.resourceIds = resourceIds;
		return this;
	}



	/**
	 * Set the value of property {@code authorizedGrantTypes}.
	 *
	 * @param authorizedGrantTypes the authorizedGrantTypes to 
	 */
	public ClientBuilder authorizedGrantTypes(Set<String> authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
		return this;
	}



	/**
	 * Set the value of property {@code registeredRedirectUris}.
	 *
	 * @param registeredRedirectUris the registeredRedirectUris to 
	 */
	public ClientBuilder registeredRedirectUris(Set<String> registeredRedirectUris) {
		this.registeredRedirectUris = registeredRedirectUris;
		return this;
	}



	/**
	 * Set the value of property {@code autoApproveScopes}.
	 *
	 * @param autoApproveScopes the autoApproveScopes to 
	 */
	public ClientBuilder autoApproveScopes(Set<String> autoApproveScopes) {
		this.autoApproveScopes = autoApproveScopes;
		return this;
	}



	/**
	 * Set the value of property {@code authorities}.
	 *
	 * @param authorities the authorities to 
	 */
	public ClientBuilder authorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
		return this;
	}



	/**
	 * Set the value of property {@code accessTokenValiditySeconds}.
	 *
	 * @param accessTokenValiditySeconds the accessTokenValiditySeconds to 
	 */
	public ClientBuilder accessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
		return this;
	}



	/**
	 * Set the value of property {@code refreshTokenValiditySeconds}.
	 *
	 * @param refreshTokenValiditySeconds the refreshTokenValiditySeconds to 
	 */
	public ClientBuilder refreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
		return this;
	}



	/**
	 * Set the value of property {@code additionalInformation}.
	 *
	 * @param additionalInformation the additionalInformation to 
	 */
	public ClientBuilder additionalInformation(Map<String, Object> additionalInformation) {
		this.additionalInformation = additionalInformation;
		return this;
	}



	/**
	 * Set the value of property {@code avatar}.
	 *
	 * @param avatar the avatar to 
	 */
	public ClientBuilder avatar(String avatar) {
		this.avatar = avatar;
		return this;
	}



	/**
	 * Set the value of property {@code thumbnail}.
	 *
	 * @param thumbnail the thumbnail to 
	 */
	public ClientBuilder thumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
		return this;
	}



	public Client build() {
		Client client = new Client();
		client.setClientId(clientId);
		client.setClientSecret(clientSecret);
		client.setScope(scope);
		client.setResourceIds(resourceIds);
		client.setAuthorizedGrantTypes(authorizedGrantTypes);
		client.setRegisteredRedirectUris(registeredRedirectUris);
		client.setAutoApproveScopes(autoApproveScopes);
		client.setAuthorities(authorities);
		client.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
		client.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);
		client.setAdditionalInformation(additionalInformation);
		client.setAvatar(avatar);
		client.setThumbnail(thumbnail);
		return client;
	}

}
