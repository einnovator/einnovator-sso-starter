package com.einnovator.sso.client.manager;

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.config.SsoClientSecurityConfigurer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes=SsoClientSecurityConfigurer.class, webEnvironment=WebEnvironment.MOCK)
public class GroupManagerTests {

	@SuppressWarnings("unused")
	@Autowired
	private SsoClient client;
	
	@Test
	public void contextLoads() {
	}

}
