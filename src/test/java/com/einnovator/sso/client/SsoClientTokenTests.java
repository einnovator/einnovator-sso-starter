package com.einnovator.sso.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.UUID;

import org.einnovator.util.UriUtils;
import org.einnovator.util.model.AddressBuilder;
import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.config.SsoClientConfiguration;
import org.einnovator.sso.client.config.SsoClientSecurityConfigurer;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.model.UserBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= {SsoClientTokenTests.TestConfig.class, SsoClientSecurityConfigurer.class}, webEnvironment=WebEnvironment.MOCK)
@EnableCaching
@TestPropertySource(properties = { "sso.server=http://localhost:2001" })
public class SsoClientTokenTests {

	@Autowired
	private SsoClient client;
	
	@SuppressWarnings("unused")
	@Autowired
	private SsoClientConfiguration config;
	
	private final String TEST_CLIENT_ID = "einnovator";
	private final String TEST_CLIENT_SECRET = "einnovator$123";

	
	@Configuration
	static class TestConfig {

		
	}
	
	@Before
	public void init() {
		client.setupClientToken(TEST_CLIENT_ID, TEST_CLIENT_SECRET);
	}
	

	@Test
	public void createUserAndGetTest() {
		String username = "tdd-" + UUID.randomUUID().toString();
		User user = new UserBuilder().username(username).email(username + "@test.org")
				.password(("Pass123!!-" + username).getBytes())
				.address(new AddressBuilder().country("USA").city("NY").postalCode("12345").build()).build();
		URI uri = client.createUser(user);
		assertNotNull(uri);
		String id = UriUtils.extractId(uri);
		assertNotNull(id);
		assertFalse(id.isEmpty());
		User user2 = client.getUser(id);
		assertNotNull(user2);
		assertEquals(id, user2.getId());
		assertEquals(user.getUsername(), user2.getUsername());
	}

	
	
}
