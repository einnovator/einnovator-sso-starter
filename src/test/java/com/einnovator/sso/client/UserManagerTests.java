package com.einnovator.sso.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.SsoClientSecurityConfigurer;
import org.einnovator.sso.client.manager.GroupManager;
import org.einnovator.sso.client.manager.UserManager;
import org.einnovator.sso.client.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SsoClientSecurityConfigurer.class, webEnvironment=WebEnvironment.MOCK)
@SuppressWarnings("unused")
public class UserManagerTests {

	private final String TEST_USER = "tdd@einnovator.org";
	private final String TEST_USER2 = "jsimao71@gmail.com";
	private final String TEST_USER3 = "jobs@einnovator.org";
	
	private final String TEST_PASSWORD = "Einnovator123!!";
	
	@Autowired
	private SsoClient client;
	
	@Autowired
	private UserManager userManager;

	@Autowired
	private GroupManager groupManager;
	
	@Test
	public void contextLoads() {
	}
	
	@Before
	public void init() {
		client.getToken(TEST_USER2, TEST_PASSWORD);
	}
	
	@Test
	public void getUserTest() {
		User user = userManager.getUser(TEST_USER2);
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
