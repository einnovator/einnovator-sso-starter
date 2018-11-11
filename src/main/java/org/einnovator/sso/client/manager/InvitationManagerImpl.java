package org.einnovator.sso.client.manager;

import java.net.URI;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.model.Invitation;
import org.einnovator.sso.client.model.InvitationFilter;
import org.einnovator.sso.client.model.InvitationStats;

public class InvitationManagerImpl implements InvitationManager {

	
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SsoClient client;

	@SuppressWarnings("unused")
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
	public URI invite(Invitation invitation, Boolean sendMail, String redirectUri) {
		try {
			URI uri = client.invite(invitation, sendMail, redirectUri);
			if (uri == null) {
				logger.error("invite: " + invitation);
			}
			return uri;
		} catch (RuntimeException e) {
			logger.error("invite: " + invitation + " " + e);
			return null;
		}
	}


	@Override
	public Invitation getInvitation(String id) {
		if (id == null) {
			return null;
		}
		try {
			Invitation invitation = client.getInvitation(id);
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
	public InvitationStats getInvitationStats() {
		try {
			InvitationStats stats = client.getInvitationStats();
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
	public URI getInvitationToken(String id, Boolean sendMail) {
		if (id == null) {
			return null;
		}
		try {
			URI token = client.getInvitationToken(id, sendMail);
			if (token == null) {
				logger.error("getInvitation: " + id + " " + sendMail);
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
	public Invitation updateInvitation(Invitation invitation) {
		try {
			client.updateInvitation(invitation);
			return invitation;
		} catch (RuntimeException e) {
			logger.error("updateInvitation: " + e + "  " + invitation);
			return null;
		}
	}

}
