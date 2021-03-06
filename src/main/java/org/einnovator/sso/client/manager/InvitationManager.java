package org.einnovator.sso.client.manager;

import java.net.URI;

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.model.Invitation;
import org.einnovator.sso.client.model.InvitationStats;
import org.einnovator.sso.client.modelx.InvitationFilter;
import org.einnovator.sso.client.modelx.InvitationOptions;
import org.springframework.cache.Cache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * High-level API for {@code Invitation}s remote operations.
 * 
 * @see Invitation
 * @see SsoClient
 * @author support@einnovator.org
 *
 */
public interface InvitationManager {

	/**
	 * List {@code Invitation}s.
	 * 
	 * 
	 * <p><b>Required Security Credentials</b>: any, but results depend user, and each {@code Group}, parent and root {@code Group} privacy settings.
	 * 
	 * @param filter a {@code InvitationFilter}
	 * @param pageable a {@code Pageable} (optional)

	 * @return a {@code Page} with {@code Invitation}s, or null if request failed
	 */	
	Page<Invitation> listInvitations(InvitationFilter filter, Pageable pageable);
	
	/**
	 * Get {@code Invitation} with specified identifier.
	 * 
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), owner.
	 *
	 * @param id the identifier (UUID)
	 * @param options optional  {@code InvitationOptions}

	 * @return the {@code Invitation} if found, or null if not found or request failed
	 */
	Invitation getInvitation(String id, InvitationOptions options);

	/**
	 * Create a new {@code Invitation}.
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), any. 
	 * 
	 * @param invitation the {@code Invitation}
	 * @param options the {@code InvitationOptions}

	 * @return the location {@code URI} for the created {@code Invitation}, or null if request failed
	 */
	URI invite(Invitation invitation, InvitationOptions options);

	/**
	 * Update existing {@code Invitation}
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), or owner.
	 * 
	 * @param invitation the {@code Invitation}
	 * @param options optional  {@code InvitationOptions}

	 * @return the same {@code Invitation}, or null if request failed
	 */
	Invitation updateInvitation(Invitation invitation, InvitationOptions options);

	/**
	 * Get {@code InvitationStats} with {@code Invitation} statistics.
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN).
	 *
	 * @param options optional  {@code InvitationOptions}
	 * @return the {@code InvitationStats}, or null if request failed
	 */
	InvitationStats getInvitationStats(InvitationOptions options);
	
	/**
	 * Get invitation {@code URI} with token for specified {@code Invitation}.
	 * 
	 * 
	 * <p><b>Required Security Credentials</b>: Client, Admin (global role ADMIN), owner.
	 *
	 * @param id the identifier of the {@code Invitation} (UUID)
	 * @param options optional  {@code InvitationOptions}
	 * @return the invitation token as an {@code URI}, or null if request failed
	 */
	URI getInvitationToken(String id, InvitationOptions options);

	/**
	 * Delete existing {@code Invitation}
	 * 
	 * <b>Required Security Credentials</b>: Client, Admin, or owner.
	 * 
	 * @param id the identifier (UUID)
	 * @param options optional  {@code InvitationOptions}
	 * @return true if {@code User} was deleted, or false if request failed
	 */
	boolean deleteInvitation(String id, InvitationOptions options);
	
	
	//
	// Caching
	//
	
	/**
	 * Evict cache entries for {@code User}.
	 * 
	 * @param userId the {@code User} uuid or username
	 */
	void evictCachesForUser(String userId);
	

	/**
	 * Clear {@code Invitation} caches.
	 * 
	 */
	void clearCaches();
	
	/**
	 * Get the cache for {@code Invitation}s.
	 * 
	 * @return the {@code Cache}
	 */
	Cache getInvitationCache();
	
	/**
	 * Get the cache for {@code Invitation}s counts.
	 * 
	 * @return the {@code Cache}
	 */
	Cache getInvitationCountCache();
	
	/**
	 * Get the cache for {@code Invitation}s stats.
	 * 
	 * @return the {@code Cache}
	 */
	Cache getInvitationStatsCache();
}
