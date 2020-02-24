package org.einnovator.sso.client.manager;

import java.net.URI;

import org.einnovator.sso.client.config.SsoClientContext;
import org.einnovator.sso.client.model.Invitation;
import org.einnovator.sso.client.modelx.InvitationFilter;
import org.einnovator.sso.client.modelx.InvitationOptions;
import org.einnovator.sso.client.model.InvitationStats;
import org.springframework.cache.Cache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvitationManager {

	URI invite(Invitation invitation, Boolean sendMail, SsoClientContext context);

	Page<Invitation> listInvitations(InvitationFilter filter, Pageable pageable, SsoClientContext context);
	
	Invitation getInvitation(String id, InvitationOptions options, SsoClientContext context);

	Invitation updateInvitation(Invitation invitation, SsoClientContext context);
	Invitation updateInvitation(Invitation invitation, Boolean publish, SsoClientContext context);

	InvitationStats getInvitationStats(SsoClientContext context);
	
	URI getInvitationToken(String id, Boolean sendMail, SsoClientContext context);

	/**
	 * Delete existing {@code Invitation}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin, or owner.
	 * 
	 * @param id the identifier (UUID)
	 * @param context optional {@code SsoClientContext}
	 * @return true if successful, false otherwise (error is set in context is not null and not singleton)
	 */
	boolean deleteInvitation(String id, SsoClientContext context);
	
	void evictCachesForUser(String userId);
	void clearCaches();
	Cache getInvitationCache();
	Cache getInvitationCountCache();
	Cache getInvitationStatsCache();
}
