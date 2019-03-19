package com.einnovator.sso.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.config.SsoClientConfiguration;
import org.einnovator.sso.client.config.SsoClientSecurityConfigurer;
import org.einnovator.sso.client.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { LegacySsoClientTests.TestConfig.class,
		SsoClientSecurityConfigurer.class }, webEnvironment = WebEnvironment.MOCK)
@EnableCaching
@TestPropertySource(properties = { "sso.server=http://localhost:28081/auth" })
public class LegacySsoClientTests {

	@Autowired
	private SsoClient client;

	@Autowired
	private SsoClientConfiguration config;

	private final String TEST_USER = "tdd@einnovator.org";
	private final String TEST_USER2 = "jsimao71@gmail.com";
	private final String TEST_USER1 = "info@einnovator.org";

	@SuppressWarnings("unused")
	private final String TEST_USER3 = "jobs@einnovator.org";

	private final String TEST_PASSWORD = "Einnovator123!!";
	private final String TEST_USERX1 = "info+100@einnovator.org";

	public static final String CLIENT_ID = "greenfence";
	public static final String CLIENT_SECRET = "greenfence$123";

	@Configuration
	static class TestConfig {

	}

	@Before
	public void init() {
		config.setClientId(CLIENT_ID);
		config.setClientSecret(CLIENT_SECRET);
		client.getToken(TEST_USER2, TEST_PASSWORD);
	}

	@Test
	public void getTokenTest() {
		OAuth2AccessToken token = client.getToken(TEST_USER2, TEST_PASSWORD);
		System.out.println(token);
	}


	@Test
	public void getExistingUserTest() {
		String username = TEST_USER2;
		User user = client.getUser(username);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		System.out.println(user);
		System.out.println(user.getGroups());
		System.out.println(user.getMembership());

		username = TEST_USER1;
		user = client.getUser(username);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		System.out.println(user);
		System.out.println(user.getGroups());
		System.out.println(user.getMembership());

	}


}
