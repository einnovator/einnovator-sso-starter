package org.einnovator.sso.client.model;
		

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.einnovator.util.MappingUtils;
import org.einnovator.util.model.EntityBase;
import org.einnovator.util.model.ToStringCreator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.Jackson2ArrayOrStringDeserializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
		

/**
 * A {@code Client} representing an authorized application.
 * 
 *
 * @author support@einnovator.org
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Client extends EntityBase {	

	@JsonProperty("client_id")
	private String clientId;
	
	@JsonProperty("client_secret")
	private String clientSecret;

	@JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
	private Set<String> scope = Collections.emptySet();

	@JsonProperty("resource_ids")
	@JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
	private Set<String> resourceIds = Collections.emptySet();

	@JsonProperty("authorized_grant_types")
	@JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
	private Set<String> authorizedGrantTypes = Collections.emptySet();
	
	@JsonProperty("redirect_uri")
	@JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
	private Set<String> registeredRedirectUris;
	
	@JsonProperty("autoapprove")
	@JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
	private Set<String> autoApproveScopes;
	
	@JsonIgnore
	private List<GrantedAuthority> authorities = Collections.emptyList();

	@JsonProperty("access_token_validity")
	private Integer accessTokenValiditySeconds;

	@JsonProperty("refresh_token_validity")
	private Integer refreshTokenValiditySeconds;
	
	private Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();

	private String avatar;
	
	private String thumbnail;

	@JsonIgnore
	private ClientDetails details;

	/**
	 * Create instance of {@code Client}.
	 *
	 */
	public Client() {
	}
	
	/**
	 * Create instance of {@code Client}.
	 *
	 * @param obj a prototype
	 */
	public Client(Object obj) {
		super(obj);
	}


	/**
	 * Get the value of property {@code clientId}.
	 *
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * Set the value of property {@code clientId}.
	 *
	 * @param clientId the value of property clientId
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * Get the value of property {@code clientSecret}.
	 *
	 * @return the clientSecret
	 */
	public String getClientSecret() {
		return clientSecret;
	}

	/**
	 * Set the value of property {@code clientSecret}.
	 *
	 * @param clientSecret the value of property clientSecret
	 */
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	/**
	 * Get the value of property {@code scope}.
	 *
	 * @return the scope
	 */
	public Set<String> getScope() {
		return scope;
	}

	/**
	 * Set the value of property {@code scope}.
	 *
	 * @param scope the value of property scope
	 */
	public void setScope(Set<String> scope) {
		this.scope = scope;
	}

	/**
	 * Get the value of property {@code resourceIds}.
	 *
	 * @return the resourceIds
	 */
	public Set<String> getResourceIds() {
		return resourceIds;
	}

	/**
	 * Set the value of property {@code resourceIds}.
	 *
	 * @param resourceIds the value of property resourceIds
	 */
	public void setResourceIds(Set<String> resourceIds) {
		this.resourceIds = resourceIds;
	}

	/**
	 * Get the value of property {@code authorizedGrantTypes}.
	 *
	 * @return the authorizedGrantTypes
	 */
	public Set<String> getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	/**
	 * Set the value of property {@code authorizedGrantTypes}.
	 *
	 * @param authorizedGrantTypes the value of property authorizedGrantTypes
	 */
	public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	/**
	 * Get the value of property {@code registeredRedirectUris}.
	 *
	 * @return the registeredRedirectUris
	 */
	public Set<String> getRegisteredRedirectUris() {
		return registeredRedirectUris;
	}

	/**
	 * Set the value of property {@code registeredRedirectUris}.
	 *
	 * @param registeredRedirectUris the value of property registeredRedirectUris
	 */
	public void setRegisteredRedirectUris(Set<String> registeredRedirectUris) {
		this.registeredRedirectUris = registeredRedirectUris;
	}

	/**
	 * Get the value of property {@code autoApproveScopes}.
	 *
	 * @return the autoApproveScopes
	 */
	public Set<String> getAutoApproveScopes() {
		return autoApproveScopes;
	}

	/**
	 * Set the value of property {@code autoApproveScopes}.
	 *
	 * @param autoApproveScopes the value of property autoApproveScopes
	 */
	public void setAutoApproveScopes(Set<String> autoApproveScopes) {
		this.autoApproveScopes = autoApproveScopes;
	}

	/**
	 * Get the value of property {@code authorities}.
	 *
	 * @return the authorities
	 */
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * Set the value of property {@code authorities}.
	 *
	 * @param authorities the value of property authorities
	 */
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * Get the value of property {@code accessTokenValiditySeconds}.
	 *
	 * @return the accessTokenValiditySeconds
	 */
	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	/**
	 * Set the value of property {@code accessTokenValiditySeconds}.
	 *
	 * @param accessTokenValiditySeconds the value of property accessTokenValiditySeconds
	 */
	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	/**
	 * Get the value of property {@code refreshTokenValiditySeconds}.
	 *
	 * @return the refreshTokenValiditySeconds
	 */
	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	/**
	 * Set the value of property {@code refreshTokenValiditySeconds}.
	 *
	 * @param refreshTokenValiditySeconds the value of property refreshTokenValiditySeconds
	 */
	public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	/**
	 * Get the value of property {@code additionalInformation}.
	 *
	 * @return the additionalInformation
	 */
	public Map<String, Object> getAdditionalInformation() {
		return additionalInformation;
	}

	/**
	 * Set the value of property {@code additionalInformation}.
	 *
	 * @param additionalInformation the value of property additionalInformation
	 */
	public void setAdditionalInformation(Map<String, Object> additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	/**
	 * Get the value of property {@code avatar}.
	 *
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * Set the value of property {@code avatar}.
	 *
	 * @param avatar the value of property avatar
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * Get the value of property {@code thumbnail}.
	 *
	 * @return the thumbnail
	 */
	public String getThumbnail() {
		return thumbnail;
	}

	/**
	 * Set the value of property {@code thumbnail}.
	 *
	 * @param thumbnail the value of property thumbnail
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * Get the value of property {@code details}.
	 *
	 * @return the details
	 */
	public ClientDetails getDetails() {
		return details;
	}

	/**
	 * Set the value of property {@code details}.
	 *
	 * @param details the value of property details

	 */
	public void setDetails(ClientDetails details) {
		this.details = details;
	}

	// With
	
	/**
	 * Set the value of property {@code clientId}.
	 *
	 * @param clientId the value of property clientId
	 * @return this {@code Client}
	 */
	public Client withClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}

	/**
	 * Set the value of property {@code clientSecret}.
	 *
	 * @param clientSecret the value of property clientSecret
	 * @return this {@code Client}
	 */
	public Client withClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
		return this;
	}

	/**
	 * Set the value of property {@code scope}.
	 *
	 * @param scope the value of property scope
	 * @return this {@code Client}
	 */
	public Client withScope(Set<String> scope) {
		this.scope = scope;
		return this;
	}

	/**
	 * Set the value of property {@code resourceIds}.
	 *
	 * @param resourceIds the value of property resourceIds
	 * @return this {@code Client}
	 */
	public Client withResourceIds(Set<String> resourceIds) {
		this.resourceIds = resourceIds;
		return this;
	}

	/**
	 * Set the value of property {@code authorizedGrantTypes}.
	 *
	 * @param authorizedGrantTypes the value of property authorizedGrantTypes
	 * @return this {@code Client}
	 */
	public Client withAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
		return this;
	}

	/**
	 * Set the value of property {@code registeredRedirectUris}.
	 *
	 * @param registeredRedirectUris the value of property registeredRedirectUris
	 * @return this {@code Client}
	 */
	public Client withRegisteredRedirectUris(Set<String> registeredRedirectUris) {
		this.registeredRedirectUris = registeredRedirectUris;
		return this;
	}

	/**
	 * Set the value of property {@code autoApproveScopes}.
	 *
	 * @param autoApproveScopes the value of property autoApproveScopes
	 * @return this {@code Client}
	 */
	public Client withAutoApproveScopes(Set<String> autoApproveScopes) {
		this.autoApproveScopes = autoApproveScopes;
		return this;
	}

	/**
	 * Set the value of property {@code authorities}.
	 *
	 * @param authorities the value of property authorities
	 * @return this {@code Client}
	 */
	public Client withAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
		return this;
	}

	/**
	 * Set the value of property {@code accessTokenValiditySeconds}.
	 *
	 * @param accessTokenValiditySeconds the value of property accessTokenValiditySeconds
	 * @return this {@code Client}
	 */
	public Client withAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
		return this;
	}

	/**
	 * Set the value of property {@code refreshTokenValiditySeconds}.
	 *
	 * @param refreshTokenValiditySeconds the value of property refreshTokenValiditySeconds
	 * @return this {@code Client}
	 */
	public Client withRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
		return this;
	}

	/**
	 * Set the value of property {@code additionalInformation}.
	 *
	 * @param additionalInformation the value of property additionalInformation
	 * @return this {@code Client}
	 */
	public Client withAdditionalInformation(Map<String, Object> additionalInformation) {
		this.additionalInformation = additionalInformation;
		return this;
	}

	/**
	 * Set the value of property {@code avatar}.
	 *
	 * @param avatar the value of property avatar
	 * @return this {@code Client}
	 */
	public Client withAvatar(String avatar) {
		this.avatar = avatar;
		return this;
	}

	/**
	 * Set the value of property {@code thumbnail}.
	 *
	 * @param thumbnail the value of property thumbnail
	 * @return this {@code Client}
	 */
	public Client withThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
		return this;
	}

	/**
	 * Set the value of property {@code details}.
	 *
	 * @param details the value of property details
	 * @return this {@code Client}
	 */
	public Client withDetails(ClientDetails details) {
		this.details = details;
		return this;
	}

	@Override
	public ToStringCreator toString1(ToStringCreator builder) {
		return builder
				.append("clientId", clientId)
				.append("clientSecret", clientSecret)
				.append("scope", scope)
				.append("resourceIds", resourceIds)
				.append("authorizedGrantTypes", authorizedGrantTypes)
				.append("registeredRedirectUris", registeredRedirectUris)
				.append("authorities", authorities)
				.append("accessTokenValiditySeconds", accessTokenValiditySeconds)
				.append("refreshTokenValiditySeconds", refreshTokenValiditySeconds)
				.append("additionalInformation", additionalInformation)
				.append("avatar", avatar)
				.append("thumbnail", thumbnail)
				;
	} 

	public ClientDetails toClientDetails() {
		return MappingUtils.convert(this, BaseClientDetails.class);
	}
}
