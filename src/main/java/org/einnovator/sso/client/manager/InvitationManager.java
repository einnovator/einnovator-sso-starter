package org.einnovator.sso.client.manager;

import java.net.URI;

import org.einnovator.sso.client.model.Invitation;
import org.einnovator.sso.client.model.InvitationFilter;
import org.einnovator.sso.client.model.InvitationStats;
import org.springframework.cache.Cache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvitationManager {

	URI invite(Invitation invitation, Boolean sendMail, String redirectUri);

	Page<Invitation> listInvitations(InvitationFilter filter, Pageable pageable);
	
	Invitation getInvitation(String id);

	Invitation updateInvitation(Invitation invitation);
	Invitation updateInvitation(Invitation invitation, Boolean publish);

	InvitationStats getInvitationStats();
	
	URI getInvitationToken(String id, Boolean sendMail);

	void evictCachesForUser(String userId);
	void clearCaches();
	Cache getInvitationCache();
	Cache getInvitationCountCache();
	Cache getInvitationStatsCache();
}
