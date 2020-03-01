/**
 * 
 * Root package for SSO Gateway Client.
 * 
 * <p>{@link SsoClient} provides a "low-level" API with methods for all server endpoints and resource types. 
 * <p>Including: {@link User}, {@link Group}, {@link Member}, {@link Invitation}, {@link Role}, {@link Client}.
 * <p>For caching enabled "high-level" API, see Manager classes.
 * <p>{@link SsoClientConfiguration} specifies configuration details, including server URL and client credentials.
 * <p>Application configuration is usually done via {@code application.yml} file.
 * 
 * <p>Example configuration in {@code application.yaml}:
 * <p>sso:
 * <p>  server : https://sso.mydomain.com
 * <p>  client-id: myapp
 * <p>  client-secret: myapp$secret
 * <p>  registration:
 * <p>    auto: true
 * <p>    roles:
 * <p>    - name: "CUSTOMER_MANAGER"
 * <p>      display-name: "Customer Manager"
 * <p>      type: GLOBAL
 * <p>      description: "User with this role can manage Customers" 
 *
 *
 * <p>Example: Obtaining an access token
 * <p>{@code @Autowired SsoClient SsoClient;}
 * <p>{@code OAuth2AccessToken token = ssoClient.getToken(TEST_USER2, TEST_PASSWORD) };
 * 
 * <p>Example: Listing {@code User}s with default page size (50) and no filtering, and default serve
 * <p>{@code @Autowired UserManager userManager;}
 * <p>{@code Page<User> users = userManager.listUsers(null, null, null)};
 * 
 * <p>Example: List at most 1000 filtered {@code User}s with letter "m" in username, and gender FEMALE, using 
 * <p>{@code Page<User> users = userManager.listUsers(new UserFilter().withQ("m").withGender(FEMALE), new PageRequest(0, 1000), null)};
 *
 * <p>Example: Listing {@code Group}s with default page size (50) and no filtering, and default serve
 * <p>{@code @Autowired GroupManager groupManager;}
 * <p>{@code Page<Group> group = userManager.listGroup(new GroupFiler.withType(GroupType.ORGANIZATION), null, null)};
 *
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
