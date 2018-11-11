package org.einnovator.sso.client.manager;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;

public class SsoCacheResolver implements CacheResolver {

	private CacheManager cacheManager;
	
	public SsoCacheResolver(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
		 return (Collection<? extends Cache>) context.getOperation().getCacheNames().stream()
		            .map(cacheName -> cacheManager.getCache(cacheName))
		            .filter(cache -> cache != null)
		            .collect(Collectors.toList());
	}
}
