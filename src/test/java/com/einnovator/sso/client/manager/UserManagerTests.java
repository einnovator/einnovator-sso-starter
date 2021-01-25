package com.einnovator.sso.client.manager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.config.SsoClientConfiguration;
import org.einnovator.sso.client.config.SsoClientSecurityConfigurer;
import org.einnovator.sso.client.manager.GroupManager;
import org.einnovator.sso.client.manager.UserManager;
import org.einnovator.sso.client.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes=SsoClientSecurityConfigurer.class, webEnvironment=WebEnvironment.MOCK)
@SuppressWarnings("unused")
@TestPropertySource(properties = { "sso.server=http://localhost:2001" })
public class UserManagerTests {

	private final String TEST_USER = "tdd@einnovator.org";
	private final String TEST_USER2 = "jsimao71@gmail.com";
	private final String TEST_USER3 = "jobs@einnovator.org";
	
	private final String TEST_PASSWORD = "Einnovator123!!";
	
	private final String CLIENT_ID = "application";

	private final String CLIENT_SECRET = "application$123";

	@Autowired
	private SsoClient client;

	@Autowired
	private SsoClientConfiguration config;

	@Autowired
	private UserManager userManager;

	@Autowired
	private GroupManager groupManager;
	
	@Test
	public void contextLoads() {
	}
	
	@BeforeEach
	public void init() {
		config.setClientId(CLIENT_ID);
		config.setClientSecret(CLIENT_SECRET);
		client.getToken(TEST_USER2, TEST_PASSWORD);
	}
	
	@Test
	public void getUserTest() {
		String username = TEST_USER2;
		User user = userManager.getUser(username, null);
		assertNotNull(user);
		System.out.println(user);
	}

	@Test
	public void listUsersTest() {
		Page<User> users = userManager.listUsers(null, null);
		assertNotNull(users);
		assertFalse(users.getNumberOfElements()==0);
		assertFalse(users.getContent().isEmpty());
		System.out.println("#" + users.getNumberOfElements());	
		for (User user: users) {
			System.out.println(user);			
		}
	}
}
