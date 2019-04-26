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
import org.springframework.security.oauth2.provider.client.JacksonArrayOrStringDeserializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Client extends EntityBase {	

	@JsonProperty("client_id")
	private String clientId;
	
	@JsonProperty("client_secret")
	private String clientSecret;

	@org.codehaus.jackson.map.annotate.JsonDeserialize(using = JacksonArrayOrStringDeserializer.class)
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


	public Client() {
	}
	
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
	 * @param clientId the clientId to set
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
	 * @param clientSecret the clientSecret to set
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
	 * @param scope the scope to set
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
	 * @param resourceIds the resourceIds to set
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
	 * @param authorizedGrantTypes the authorizedGrantTypes to set
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
	 * @param registeredRedirectUris the registeredRedirectUris to set
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
	 * @param autoApproveScopes the autoApproveScopes to set
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
	 * @param authorities the authorities to set
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
	 * @param accessTokenValiditySeconds the accessTokenValiditySeconds to set
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
	 * @param refreshTokenValiditySeconds the refreshTokenValiditySeconds to set
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
	 * @param additionalInformation the additionalInformation to set
	 */
	public void setAdditionalInformation(Map<String, Object> additionalInformation) {
		this.additionalInformation = additionalInformation;
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
	 * @param details the details to set
	 */
	public void setDetails(ClientDetails details) {
		this.details = details;
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
	 * @param avatar the avatar to set
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
	 * @param thumbnail the thumbnail to set
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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
