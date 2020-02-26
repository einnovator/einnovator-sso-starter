/**
 * 
 * Root package for SSO Gateway Client.
 * 
 * <p>{@link SsoClient} provides a "low-level" API with methods for all server endpoints and resource types. 
 * <p>Including: {@link User}, {@link Group}, {@link Member}, {@link Invitation}, {@link Role}, {@link Client}.
 * <p>For caching enabled "high-level" API, see Manager classes.
 * <p>{@link SsoClientConfiguration} specifies configuration details, including server URL and client credentials.
 * 
 * @see org.einnovator.sso.client.manager.UserManager
 * @see org.einnovator.sso.client.manager.GroupManager
 * @see org.einnovator.sso.client.manager.RoleManager
 * @see org.einnovator.sso.client.manager.InvitationManager
 * @see org.einnovator.sso.client.manager.ClientManager
 * @author support@einnovator.org
 *
 */
package org.einnovator.sso.client;

import org.einnovator.sso.client.model.Client;
import org.einnovator.sso.client.model.Group;
import org.einnovator.sso.client.model.Invitation;
import org.einnovator.sso.client.model.Member;
import org.einnovator.sso.client.model.Role;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.config.SsoClientConfiguration;
