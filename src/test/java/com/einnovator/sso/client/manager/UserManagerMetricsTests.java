package com.einnovator.sso.client.manager;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.config.SsoClientSecurityConfigurer;
import org.einnovator.sso.client.manager.GroupManager;
import org.einnovator.sso.client.manager.UserManager;
import org.einnovator.sso.client.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SsoClientSecurityConfigurer.class, webEnvironment=WebEnvironment.MOCK)
@SuppressWarnings("unused")
public class UserManagerMetricsTests {

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
	
	@Before
	public void init() {
		client.getToken(TEST_USER2, TEST_PASSWORD);
	}
	
	@Test
	public void getUserTest() {
		int n = 10;
		long sdt= 0;
		long sdt2 = 0;
		for (int i=0; i<n; i++) {
			userManager.clearCache();
			long t0 = System.currentTimeMillis();
			User user = userManager.getUser(TEST_USER2);
			long t1 = System.currentTimeMillis();
			long dt = t1-t0;
			sdt += dt;
			System.out.println(i + " (" + dt + "ms): " + user);
			assertNotNull(user);

			t0 = System.currentTimeMillis();
			user = userManager.getUser(TEST_USER2);
			t1 = System.currentTimeMillis();
			long dt2 = t1-t0;
			System.out.println(i + " (" + dt2 + "ms): " + user);
			sdt2 += dt2;
			assertTrue(dt>dt2);
		}
		System.out.println((sdt/n) + "ms " + (sdt2/n) + "ms");

	}

	
	
}
