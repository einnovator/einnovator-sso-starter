package org.einnovator.sso.client.config;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.util.IOUtil;
import org.einnovator.util.PathUtil;
import org.einnovator.util.UriUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.client.RestTemplate;

//@Configuration
//@EnableResourceServer
public class OAuth2ResourcesConfigurer extends ResourceServerConfigurerAdapter {

	private final Log logger = LogFactory.getLog(getClass());

	public static final String RESOURCE_OPENDID = "userinfo";
	
	@Autowired
	private SsoClientConfiguration config;
	
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			.and()
			//.antMatcher("/api/**")
			.requestMatcher(new BearerAuthorizationHeaderMatcher())
		   	.authorizeRequests().anyRequest().authenticated();
		   
	}
	
	public static class BearerAuthorizationHeaderMatcher implements RequestMatcher {
		
		BearerTokenExtractor tokenExtractor = new BearerTokenExtractor();
		
		public BearerAuthorizationHeaderMatcher() {
		}
		
		@Override
		public boolean matches(HttpServletRequest request)  {
			Authentication authentication = tokenExtractor.extract(request);
			return authentication!=null;
		}
	}
	

	//@Bean
	public  DefaultTokenServices tokenServices() throws Exception {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(tokenStore());
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setReuseRefreshToken(true);
		tokenServices.setTokenEnhancer(accessTokenConverter());
		return tokenServices;
	}
	


	//@Bean
	public TokenStore tokenStore() throws Exception {
		JwtTokenStore store = new JwtTokenStore(accessTokenConverter());
		return store;
	}
	
	JwtAccessTokenConverter converter;
	
	public JwtAccessTokenConverter accessTokenConverter() throws Exception {
		if (converter!=null) {
			return converter;
		}
		String key = getTokenKey();
		RsaVerifier verifier = null;
		if (key!=null) {
			RSAPublicKey pubKey = makeRSAPublicKeyFromPem(key);
		    verifier = new RsaVerifier(pubKey);
		    if (logger.isDebugEnabled()) {
		    	logger.debug("accessTokenConverter:" + pubKey);
		    }			
		} else {
			logger.error("accessTokenConverter: public key not found");
		}
		converter = new JwtAccessTokenConverter();
	    converter.setVerifier(verifier);
		converter.afterPropertiesSet();
		return converter;
	}
	
	public static RSAPublicKey makeRSAPublicKeyFromPem(String key) {
		key = key.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");
        KeyFactory kf;
		try {
			kf = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
		    RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
		    return pubKey;		
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String tokenKey;
	
	public String getTokenKey() {
		if (tokenKey!=null) {
			return tokenKey;
		}
		tokenKey = readTokenKey();
		if (tokenKey==null) {
			tokenKey = getTokenKeyRemote();
			if (tokenKey!=null) {
				writeTokenKey(tokenKey);
			}			
		}
		return tokenKey;
	}
	
	private String getTokenKeyRemote() {
		RestTemplate template = new RestTemplate();
		try {
			Map<?,?> result = template.getForObject(config.getServer()+ "/oauth/token_key", Map.class);			
			Object value = result.get("value");
			if (value!=null) {
				tokenKey = value.toString();
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getTokenKey: " + tokenKey);				
			}
			return tokenKey;
		} catch (RuntimeException e) {
			logger.error("getTokenKey: Failed to get token key from authentication server:" + config.getServer() + " " + e);
			return null;
		}
	}
	
	private void writeTokenKey(String tokenKey) {
		String path = getTokenKeyPath();
		try (PrintWriter out = new PrintWriter(new FileOutputStream(path))) {
			out.println(tokenKey);
			logger.info(String.format("writeTokenKey: %s", path));
		} catch (FileNotFoundException e) {
			logger.error(String.format("writeTokenKey: failed write token key: %s %s", path, e));
		}
	}

	private String readTokenKey() {
		String path = getTokenKeyPath();
		if (!new File(path).exists()) {
			return null;
		}
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
			String key = IOUtil.readFully(in);
			logger.info(String.format("readTokenKey: %s", path));
			return key;
		} catch (IOException e) {
			logger.error(String.format("readTokenKey: failed read token key: %s %s", path, e));
			return null;
		}
	}


	private String getTokenKeyPath() {
		String filename = "sso";
		String host = UriUtils.getHost(config.getServer());
		if (host!=null && host.isEmpty() && host!="localhost" && Character.isAlphabetic(host.charAt(0))) {
			filename = host;
		}
		URI uri = UriUtils.makeURI(config.getServer());
		if (uri!=null) {
			if (uri.getPort()>0) {
				filename += "-" + uri.getPort();
			}
		}
		filename = filename + ".cert";
        String tmp = System.getProperty("java.io.tmpdir");
        return PathUtil.concat(tmp, filename, File.separator);
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenServices(tokenServices())
		.resourceId(RESOURCE_OPENDID)
		.stateless(false);
	}

}
