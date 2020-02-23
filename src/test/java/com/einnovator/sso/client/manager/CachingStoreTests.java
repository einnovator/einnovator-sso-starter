package com.einnovator.sso.client.manager;

import static org.junit.Assert.assertNotNull;

import org.einnovator.sso.client.config.SsoClientSecurityConfigurer;
import org.junit.Test;
import org.springframework.cache.CacheManager;

public class CachingStoreTests {

	@Test
	public void test() {
		CacheManager cacheManager = new SsoClientSecurityConfigurer().ssoCacheManager();
		System.out.println(cacheManager.getCacheNames());
		assertNotNull(cacheManager.getCache("User"));
		
	}

}
