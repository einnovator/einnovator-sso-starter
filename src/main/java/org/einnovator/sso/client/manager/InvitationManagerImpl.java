package org.einnovator.sso.client.manager;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.model.Invitation;
import org.einnovator.sso.client.model.InvitationStats;
import org.einnovator.sso.client.modelx.InvitationFilter;
import org.einnovator.sso.client.modelx.InvitationOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;

public class InvitationManagerImpl implements InvitationManager {
	
	public static final String CACHE_INVITATION = "Invitation";
	public static final String CACHE_INVITATION_COUNT = "Invitation:count";
	public static final String CACHE_INVITATION_STATS = "Invitation:stats";

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private SsoClient client;

	private CacheManager cacheManager;

	@Autowired
	public InvitationManagerImpl(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public InvitationManagerImpl(SsoClient client, CacheManager cacheManager) {
		this.client = client;
		this.cacheManager = cacheManager;
	}
	
	@Override
	public URI invite(Invitation invitation, InvitationOptions options) {
		try {
			URI uri = client.invite(invitation, options);
			if (uri == null) {
				logger.error("invite: " + invitation);
			}
			return uri;
		} catch (RuntimeException e) {
			logger.error("invite: " + e + " " + invitation);
			return null;
		}
	}


	@Override
	public Invitation getInvitation(String id, InvitationOptions options) {
		if (id == null) {
			return null;
		}
		try {
			Invitation invitation = client.getInvitation(id, options);
			if (invitation == null) {
				logger.error("getInvitation: " + invitation);
			}
			return invitation;
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
				logger.error("getInvitation:" + id + "  " + e);
			}
			return null;
		} catch (RuntimeException e) {
			logger.error("getInvitation: " + id + "  " + e);
			return null;
		}
	}

	

	@Override
	public Page<Invitation> listInvitations(InvitationFilter filter, Pageable pageable) {
		try {
			Page<Invitation> invitations = client.listInvitations(filter, pageable);
			if (invitations == null) {
				logger.error("listInvitations: " + filter + " " + pageable);
			}
			return invitations;
		} catch (RuntimeException e) {
			logger.error("listInvitations: " +  filter + " " + pageable + "  " + e);
			return null;
		}
	}


	@Override
	public InvitationStats getInvitationStats(InvitationOptions options) {
		try {
			InvitationStats stats = client.getInvitationStats(options);
			if (stats == null) {
				logger.error("getInvitationStats: " + stats);
			}
			return stats;
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
				logger.error("getInvitationStats:" +  e);
			}
			return null;
		} catch (RuntimeException e) {
			logger.error("getInvitation: " + e);
			return null;
		}
	}

	@Override
	public URI getInvitationToken(String id, InvitationOptions options) {
		if (id == null) {
			return null;
		}
		try {
			URI token = client.getInvitationToken(id, options);
			if (token == null) {
				logger.error("getInvitation: " + id + " " + options);
			}
			return token;
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
				logger.error("getInvitation:" + id + "  " + e);
			}
			return null;
		} catch (RuntimeException e) {
			logger.error("getInvitation: " + id + "  " + e);
			return null;
		}
	}

	@Override
	public Invitation updateInvitation(Invitation invitation, InvitationOptions options) {
		try {
			if (StringUtils.hasText(invitation.getOwner())) {
				evictCachesForUser(invitation.getOwner());
			}
			if (StringUtils.hasText(invitation.getInvitee())) {
				evictCachesForUser(invitation.getInvitee());
			}
			client.updateInvitation(invitation, options);
			return invitation;
		} catch (RuntimeException e) {
			logger.error("updateInvitation: " + e + "  " + invitation);
			return null;
		}
	}

	@Override
	@CacheEvict(value=CACHE_INVITATION, key="#id")
	public boolean deleteInvitation(String id, InvitationOptions options) {
		try {
			client.deleteInvitation(id, options);
			return true;
		} catch (RuntimeException e) {
			logger.error("deleteInvitation:" + e);
			return false;
		}
	}

	@Override
	public void evictCachesForUser(String userId) {
		Cache cache = getInvitationCache();
		if (cache!=null) {
			cache.evict(userId);
		}
		Cache cache2 = getInvitationCountCache();
		if (cache2!=null) {
			cache2.evict(userId);
		}		
		Cache cache3 = getInvitationStatsCache();
		if (cache3!=null) {
			cache3.evict(userId);
		}		

	}
	
	@Override
	public void clearCaches() {
		Cache cache = getInvitationCache();
		if (cache!=null) {
			cache.clear();
		}
		Cache cache2 = getInvitationCountCache();
		if (cache2!=null) {
			cache2.clear();
		}
		Cache cache3 = getInvitationStatsCache();
		if (cache3!=null) {
			cache3.clear();
		}

	}


	@Override
	public Cache getInvitationCache() {
		Cache cache = cacheManager.getCache(InvitationManagerImpl.CACHE_INVITATION);
		return cache;
	}

	@Override
	public Cache getInvitationCountCache() {
		Cache cache = cacheManager.getCache(InvitationManagerImpl.CACHE_INVITATION_COUNT);
		return cache;
	}

	@Override
	public Cache getInvitationStatsCache() {
		Cache cache = cacheManager.getCache(InvitationManagerImpl.CACHE_INVITATION_STATS);
		return cache;
	}

}
