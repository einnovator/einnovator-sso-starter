package com.einnovator.sso.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.List;

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.config.SsoClientSecurityConfigurer;
import org.einnovator.sso.client.manager.GroupManager;
import org.einnovator.sso.client.manager.RoleManager;
import org.einnovator.sso.client.manager.UserManager;
import org.einnovator.sso.client.model.Role;
import org.einnovator.sso.client.model.User;
import org.einnovator.util.SecurityUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SsoClientSecurityConfigurer.class, webEnvironment=WebEnvironment.MOCK)
@SuppressWarnings("unused")
@TestPropertySource(properties = { "sso.server=http://localhost:2001" })
public class RoleManagerTests {

	private final String TEST_USER = "jsimao71@gmail.com";
	private final String TEST_USER2 = "tdd@einnovator.org";
	private final String TEST_USER3 = "jobs@einnovator.org";
	
	private final String TEST_PASSWORD = "Einnovator123!!";
	
	@Autowired
	private SsoClient client;
	
	@Autowired
	private UserManager userManager;

	@Autowired
	private GroupManager groupManager;

	@Autowired
	private RoleManager roleManager;

	@Test
	public void contextLoads() {
	}
	
	@Before
	public void init() {
		OAuth2AccessToken token = client.getToken(TEST_USER, TEST_PASSWORD);
		System.out.println(token);
	}
	
	@Test
	public void listRolesForUser() {
		List<Role> roles = roleManager.listRolesForUser(TEST_USER);
		assertNotNull(roles);
		System.out.println(roles);
	}

	
	@Test
	@Ignore
	public void checkPrincipalPermissions() {
		Collection<? extends GrantedAuthority> authorities = SecurityUtil.getAuthorities();
		assertNotNull(authorities);
		for (GrantedAuthority authority: authorities) {
			System.out.println(authority);
		}
		User user = userManager.getUser(TEST_USER);
		System.out.println(user);
		System.out.println(user.getRoles());
	}
	
}
