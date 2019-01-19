package org.einnovator.sso.client.config;


import java.util.Arrays;
import java.util.List;

import org.einnovator.util.config.ConnectionConfiguration;
import org.einnovator.util.model.ObjectBase;
import org.einnovator.util.model.ToStringCreator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.config.http.SessionCreationPolicy;


/**
 * SSO configuration properties.
 * 
 * @author Jorge Simao {jorge.simao@einnovator.org}
 */
@ConfigurationProperties("sso")
public class SsoClientConfiguration extends ObjectBase {

	public static String OAUTH_TOKEN_NAME = "oauth_token";

	public static String DEFAULT_SERVER = "http://localhost:28080";

	public static String DEFAULT_REDIRECT_URI = "http://localhost/";

	private String server = DEFAULT_SERVER;
	
	private String clientId;
	
	private String clientSecret;
	
	private List<String> scopes = Arrays.asList("openid");

	private String redirectUri = DEFAULT_REDIRECT_URI;

	private String[] ignore = { "/", "/index.html", "/login**", "/register**",
			"/webjars/**", "/css/**", "/js/**", "/img/**", "/images/**", "/fonts/**", "/favicon.ico",  "/assets/**", "/theme/**", "/api**", "/api/**", "/ws/**" };
	
	private String[] ignoreInclude = {};

	private String[] csrfIgnore = { "/oauth/**", "/login**", "/logout**", "/api**", "/api/**" };
	
	private Boolean csrfEnabled;
	
	private SessionCreationPolicy sessionCreationPolicy = SessionCreationPolicy.IF_REQUIRED;
	
	@NestedConfigurationProperty
	private ConnectionConfiguration connection = new ConnectionConfiguration();

	@NestedConfigurationProperty
	private AccessConfiguration access = new AccessConfiguration();

	public SsoClientConfiguration() {
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public List<String> getScopes() {
		return scopes;
	}

	public void setScopes(List<String> scopes) {
		this.scopes = scopes;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String[] getIgnore() {
		return ignore;
	}

	public void setIgnore(String[] ignore) {
		this.ignore = ignore;
	}

	public String[] getIgnoreInclude() {
		return ignoreInclude;
	}

	public void setIgnoreInclude(String[] ignoreInclude) {
		this.ignoreInclude = ignoreInclude;
	}

	public String[] getCsrfIgnore() {
		return csrfIgnore;
	}

	public void setCsrfIgnore(String[] csrfIgnore) {
		this.csrfIgnore = csrfIgnore;
	}

	public Boolean getCsrfEnabled() {
		return csrfEnabled;
	}

	public void setCsrfEnabled(Boolean csrfEnabled) {
		this.csrfEnabled = csrfEnabled;
	}

	public ConnectionConfiguration getConnection() {
		return connection;
	}

	public void setConnection(ConnectionConfiguration connection) {
		this.connection = connection;
	}

	public SessionCreationPolicy getSessionCreationPolicy() {
		return sessionCreationPolicy;
	}

	public void setSessionCreationPolicy(SessionCreationPolicy sessionCreationPolicy) {
		this.sessionCreationPolicy = sessionCreationPolicy;
	}

	/**
	 * Get the value of property {@code access}.
	 *
	 * @return the access
	 */
	public AccessConfiguration getAccess() {
		return access;
	}

	/**
	 * Set the value of property {@code access}.
	 *
	 * @param access the access to set
	 */
	public void setAccess(AccessConfiguration access) {
		this.access = access;
	}

	/* (non-Javadoc)
	 * @see org.einnovator.util.model.ObjectBase#toString(org.einnovator.util.model.ToStringCreator)
	 */
	@Override
	public ToStringCreator toString(ToStringCreator creator) {
		return creator
				.append("server", server)
				.append("clientId", clientId)
				.append("clientSecret", clientSecret)
				.append("scopes", scopes)
				.append("redirectUri", redirectUri)
				.append("ignore", ignore)
				.append("ignoreInclude", ignoreInclude)
				.append("csrfIgnore", csrfIgnore)
				.append("csrfEnabled", csrfEnabled)
				.append("sessionCreationPolicy", sessionCreationPolicy)
				.append("access", access)
				.append("connection", connection)
				;
	}

	
	
}
