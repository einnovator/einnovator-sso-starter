package com.einnovator.sso.client;

import static com.einnovator.sso.client.Profiler.dump;
import static com.einnovator.sso.client.Profiler.run;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.config.SsoClientConfiguration;
import org.einnovator.sso.client.config.SsoClientSecurityConfigurer;
import org.einnovator.sso.client.model.User;
import org.einnovator.util.UriUtils;
import org.einnovator.util.model.Address;
import org.junit.Before;
import org.junit.Ignore;
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
@SpringBootTest(classes = { SsoClientLoadTests.TestConfig.class,
		SsoClientSecurityConfigurer.class }, webEnvironment = WebEnvironment.MOCK)
@EnableCaching
@TestPropertySource(properties = { "sso.server_=http://localhost:2001/" })
public class SsoClientLoadTests {

	@Autowired
	private SsoClient client;

	@Autowired
	private SsoClientConfiguration config;

	private final String TEST_USER = "tdd@einnovator.org";
	private final String TEST_USER2 = "jsimao71@gmail.com";
	@SuppressWarnings("unused")
	private final String TEST_USER3 = "jobs@einnovator.org";

	private final String TEST_PASSWORD = "Einnovator123!!";
	private final String TEST_USERX1 = "info+100@einnovator.org";

	public static final String CLIENT_ID = "application";
	public static final String CLIENT_SECRET = "application$123";

	@Configuration
	static class TestConfig {

	}

	@Before
	public void init() {
		config.setClientId(CLIENT_ID);
		config.setClientSecret(CLIENT_SECRET);
		//client.getToken(TEST_USER2, TEST_PASSWORD);
	}

	//@Test
	public void getTokenTest() {
		OAuth2AccessToken token = client.getToken(TEST_USER2, TEST_PASSWORD);
		System.out.println(token);
	}

	@Test
	public void getTokenNTest() {
		run(100, "getTokenNTest", () -> getTokenTest());
		dump();
	}

	@Test
	public void createUserTest() {
		client.setupClientToken();
		String username = "tdd-" + UUID.randomUUID().toString();
		User user = new User().withUsername(username).withEmail(username + "@test.org")
				.withPassword(("Pass123!!-" + username).getBytes());
		URI uri = run("SsoClientTests:createUserTest:createUser", () -> client.createUser(user, null));
		assertNotNull(uri);
		String id = UriUtils.extractId(uri);
		User user2 = run("SsoClientTests:createUserTest:getUser", () -> client.getUser(id, null));
		assertNotNull(user2);
		System.out.println(user2);
	}

	int count=0, nerrors = 0;

	public void createUserTest(String name, SsoClient client) {
		try {
			count++;
			client.setupClientToken();
			String username = "tdd-" + UUID.randomUUID().toString();
			User user = new User().withUsername(username).withEmail(username + "@test.org")
					.withPassword(("Pass123!!-" + username).getBytes());
			URI uri = run("SsoClientTests:" + name + ":createUser", () -> client.createUser(user, null));
			assertNotNull(uri);
			String id = UriUtils.extractId(uri);
			User user2 = run("SsoClientTests:" + name + ":getUser", () -> client.getUser(id, null));
			assertNotNull(user2);
			System.out.println(user2);			
		} catch (RuntimeException e) {
			nerrors++;
			System.err.println("[" + name + "](" + nerrors + "/" + count + ")" + e);			
		}
	}
	
	@Test
	public void createUserTestN() {
		run(1000, "createUserTest", () -> createUserTest());
		dump();
	}
	
	int TASKS = 50, RUNS = 20;
	@Test
	public void createUserTestNConcurrent() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(TASKS);
		class TestTask implements Runnable {
			String name;
			
			public TestTask(String name) {
				this.name = name;
			}
			
			@Override
			public void run() {		
				SsoClient client = SsoClient.makeSsoClient(TEST_USER2, TEST_PASSWORD, config);
				client.setupClientToken();

				com.einnovator.sso.client.Profiler.run(RUNS, "["+name+"] " + "createUserTest", () -> createUserTest(name, client));
			}
		}
		for (int i=0; i<TASKS; i++) {
			executor.execute(new TestTask("Task"+i));
		}
		executor.shutdown();
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		dump();
		System.out.println("(" + nerrors + "/" + count + ")");			
	}

	@Test
	@Ignore
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
