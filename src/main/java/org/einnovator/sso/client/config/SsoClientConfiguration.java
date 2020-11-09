package org.einnovator.sso.client.config;


import java.util.Arrays;
import java.util.List;

import org.einnovator.sso.client.model.SsoRegistration;
import org.einnovator.util.StringUtil;
import org.einnovator.util.config.ConnectionConfiguration;
import org.einnovator.util.model.ObjectBase;
import org.einnovator.util.model.ToStringCreator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.config.http.SessionCreationPolicy;


/**
 * SSO Client configuration properties.
 * 
 */
@ConfigurationProperties("sso")
public class SsoClientConfiguration extends ObjectBase {

	public static String SSO_EXCLUDE = "sso_exclude";
	public static String SSO_LOCAL = "sso_local";

	public static String OAUTH_TOKEN_NAME = "oauth_token";

	public static String DEFAULT_SERVER = "http://localhost:2000";

	public static String DEFAULT_REDIRECT_URI = "http://localhost/";

	private String server = DEFAULT_SERVER;

	private String authorizeServer;

	private String clientId;
	
	private String clientSecret;
	
	private List<String> scopes = Arrays.asList("openid");

	private String redirectUri = DEFAULT_REDIRECT_URI;

	private String[] ignore = { "/", "/index.html", "/login**", "/register**", "/recover**",
			"/.well-known/**",
			"/webjars/**", "/css/**", "/dist/**", "/js/**", "/img/**", "/images/**", "/fonts/**", "/favicon.ico",  "/assets/**", "/theme/**", "/api**", "/api/**", "/ws/**" };
	
	private String[] ignoreInclude = {};

	private String[] csrfIgnore = { "/oauth/**", "/login**", "/logout**", "/api**", "/api/**" };
	
	private Boolean csrfEnabled;
	
	private SessionCreationPolicy sessionCreationPolicy = SessionCreationPolicy.IF_REQUIRED;
	
	@NestedConfigurationProperty
	private ConnectionConfiguration connection = new ConnectionConfiguration();

	@NestedConfigurationProperty
	private AccessConfiguration access = new AccessConfiguration();

	@NestedConfigurationProperty
	private SsoRegistration registration;
	
	public SsoClientConfiguration() {
	}

	/**
	 * Create instance of {@code SsoClientConfiguration}.
	 *
	 * @param obj a prototype object
	 */
	public SsoClientConfiguration(Object obj) {
		super(obj);
	}

	/**
	 * Get the value of property {@code server}.
	 *
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * Set the value of property {@code server}.
	 *
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * Get the value of property {@code authorizeServer}.
	 *
	 * @return the authorizeServer
	 */
	public String getAuthorizeServer() {
		return authorizeServer;
	}

	/**
	 * Set the value of property {@code authorizeServer}.
	 *
	 * @param authorizeServer the value of property authorizeServer
	 */
	public void setAuthorizeServer(String authorizeServer) {
		this.authorizeServer = authorizeServer;
	}

	/**
	 * Get the value of property {@code authorizeServer} if set, otherwise return {@code server}.
	 *
	 * @return the value of {@code authorizeServer} or {@code server}
	 */
	public String getRequiredAuthorizeServer() {
		return StringUtil.hasText(authorizeServer) ? authorizeServer : server;
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
	 * Get the value of property {@code scopes}.
	 *
	 * @return the scopes
	 */
	public List<String> getScopes() {
		return scopes;
	}

	/**
	 * Set the value of property {@code scopes}.
	 *
	 * @param scopes the scopes to set
	 */
	public void setScopes(List<String> scopes) {
		this.scopes = scopes;
	}

	/**
	 * Get the value of property {@code redirectUri}.
	 *
	 * @return the redirectUri
	 */
	public String getRedirectUri() {
		return redirectUri;
	}

	/**
	 * Set the value of property {@code redirectUri}.
	 *
	 * @param redirectUri the redirectUri to set
	 */
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	/**
	 * Get the value of property {@code ignore}.
	 *
	 * @return the ignore
	 */
	public String[] getIgnore() {
		return ignore;
	}

	/**
	 * Set the value of property {@code ignore}.
	 *
	 * @param ignore the ignore to set
	 */
	public void setIgnore(String[] ignore) {
		this.ignore = ignore;
	}

	/**
	 * Get the value of property {@code ignoreInclude}.
	 *
	 * @return the ignoreInclude
	 */
	public String[] getIgnoreInclude() {
		return ignoreInclude;
	}

	/**
	 * Set the value of property {@code ignoreInclude}.
	 *
	 * @param ignoreInclude the ignoreInclude to set
	 */
	public void setIgnoreInclude(String[] ignoreInclude) {
		this.ignoreInclude = ignoreInclude;
	}

	/**
	 * Get the value of property {@code csrfIgnore}.
	 *
	 * @return the csrfIgnore
	 */
	public String[] getCsrfIgnore() {
		return csrfIgnore;
	}

	/**
	 * Set the value of property {@code csrfIgnore}.
	 *
	 * @param csrfIgnore the csrfIgnore to set
	 */
	public void setCsrfIgnore(String[] csrfIgnore) {
		this.csrfIgnore = csrfIgnore;
	}

	/**
	 * Get the value of property {@code csrfEnabled}.
	 *
	 * @return the csrfEnabled
	 */
	public Boolean getCsrfEnabled() {
		return csrfEnabled;
	}

	/**
	 * Set the value of property {@code csrfEnabled}.
	 *
	 * @param csrfEnabled the csrfEnabled to set
	 */
	public void setCsrfEnabled(Boolean csrfEnabled) {
		this.csrfEnabled = csrfEnabled;
	}

	/**
	 * Get the value of property {@code sessionCreationPolicy}.
	 *
	 * @return the sessionCreationPolicy
	 */
	public SessionCreationPolicy getSessionCreationPolicy() {
		return sessionCreationPolicy;
	}

	/**
	 * Set the value of property {@code sessionCreationPolicy}.
	 *
	 * @param sessionCreationPolicy the sessionCreationPolicy to set
	 */
	public void setSessionCreationPolicy(SessionCreationPolicy sessionCreationPolicy) {
		this.sessionCreationPolicy = sessionCreationPolicy;
	}

	/**
	 * Get the value of property {@code connection}.
	 *
	 * @return the connection
	 */
	public ConnectionConfiguration getConnection() {
		return connection;
	}

	/**
	 * Set the value of property {@code connection}.
	 *
	 * @param connection the connection to set
	 */
	public void setConnection(ConnectionConfiguration connection) {
		this.connection = connection;
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
	

	/**
	 * Get the value of property {@code registration}.
	 *
	 * @return the registration
	 */
	public SsoRegistration getRegistration() {
		return registration;
	}

	/**
	 * Set the value of property {@code registration}.
	 *
	 * @param registration the registration to set
	 */
	public void setRegistration(SsoRegistration registration) {
		this.registration = registration;
	}

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
				.append("registration", registration)
				;
	}

	
	
}
