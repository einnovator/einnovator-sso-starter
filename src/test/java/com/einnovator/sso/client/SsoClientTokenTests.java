package com.einnovator.sso.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.util.UUID;

import org.einnovator.util.UriUtils;
import org.einnovator.util.model.Address;
import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.config.SsoClientConfiguration;
import org.einnovator.sso.client.config.SsoClientSecurityConfigurer;
import org.einnovator.sso.client.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


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
	
	@BeforeEach
	public void init() {
		client.setupClientToken(TEST_CLIENT_ID, TEST_CLIENT_SECRET);
	}
	

	@Test
	public void createUserAndGetTest() {
		String username = "tdd-" + UUID.randomUUID().toString();
		User user = new User().withUsername(username).withEmail(username + "@test.org")
				.withPassword(("Pass123!!-" + username).getBytes())
				.withAddress(new Address().withCountry("USA").withCity("NY").withPostalCode("12345"));
		URI uri = client.createUser(user, null);
		assertNotNull(uri);
		String id = UriUtils.extractId(uri);
		assertNotNull(id);
		assertFalse(id.isEmpty());
		User user2 = client.getUser(id, null);
		assertNotNull(user2);
		assertEquals(id, user2.getId());
		assertEquals(user.getUsername(), user2.getUsername());
	}

	
	
}
