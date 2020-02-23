package com.einnovator.sso.client;

import static com.einnovator.sso.client.Profiler.dump;
import static com.einnovator.sso.client.Profiler.run;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.Random;
import java.util.UUID;

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.config.SsoClientConfiguration;
import org.einnovator.sso.client.config.SsoClientSecurityConfigurer;
import org.einnovator.sso.client.model.Group;
import org.einnovator.sso.client.model.GroupType;
import org.einnovator.sso.client.model.Invitation;
import org.einnovator.sso.client.model.InvitationType;
import org.einnovator.sso.client.model.Member;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.GroupFilter;
import org.einnovator.sso.client.modelx.UserFilter;
import org.einnovator.util.UriUtils;
import org.einnovator.util.model.Address;
import org.einnovator.util.model.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SsoClientTests.TestConfig.class,
		SsoClientSecurityConfigurer.class }, webEnvironment = WebEnvironment.MOCK)
@EnableCaching
@TestPropertySource(properties = { "sso.server=http://localhost:2001" })
public class SsoClientTests {

	@Autowired
	private SsoClient client;

	@Autowired
	private SsoClientConfiguration config;

	private final String TEST_USER = "tdd@einnovator.org";
	private final String TEST_USER2 = "jsimao71@gmail.com";
	@SuppressWarnings("unused")
	private final String TEST_USER3 = "jobs@einnovator.org";

	private final String TEST_PASSWORD = "Einnovator123!!";
	private final String TEST_USERX1 = "info+100@einnovator.org";

	public static final String CLIENT_ID = "application";
	public static final String CLIENT_SECRET = "application$123";

	@Configuration
	static class TestConfig {

	}

	@Before
	public void init() {
		config.setClientId(CLIENT_ID);
		config.setClientSecret(CLIENT_SECRET);
		client.getToken(TEST_USER2, TEST_PASSWORD);
	}

	@Test
	public void getTokenTest() {
		OAuth2AccessToken token = client.getToken(TEST_USER2, TEST_PASSWORD);
		System.out.println(token);
	}

	@Test
	public void listUsersTest() {
		OAuth2AccessToken token = client.getToken(TEST_USER2, TEST_PASSWORD);
		System.out.println(token);
		Page<User> users = client.listUsers(null, null, null);
		System.out.println(users);
		assertNotNull(users);
		assertNotNull(users.getContent());
		assertFalse(users.getNumberOfElements()==0);
		assertFalse(users.getContent().isEmpty());
	}

	@Test
	public void listUsersWithPrefixTest() {
		String prefix = "t";
		UserFilter filter = new UserFilter();
		filter.setQ("^" + prefix);
		Page<User> users = client.listUsers(filter, null, null);
		assertNotNull(users);
		assertNotNull(users);
		assertNotNull(users.getContent());
		assertFalse(users.getNumberOfElements()==0);
		assertFalse(users.getContent().isEmpty());
		for (User user : users) {
			assertTrue(user.getUsername().startsWith(prefix));
		}
	}

	@Test
	public void getExistingUserTest() {
		OAuth2AccessToken token = client.getToken(TEST_USER2, TEST_PASSWORD);
		System.out.println(token);
		String username = TEST_USER2;
		User user = client.getUser(username, null);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
	}

	@Test
	public void createUserTest() {
		String username = "tdd-" + UUID.randomUUID().toString();
		User user = new User().withUsername(username).withEmail(username + "@test.org")
				.withPassword(("Pass123!!-" + username).getBytes());
		URI uri = run("SsoClientTests:createUserTest:createUser", () -> client.createUser(user, null));
		assertNotNull(uri);
		String id = UriUtils.extractId(uri);
		User user2 = run("SsoClientTests:createUserTest:getUser", () -> client.getUser(id, null));
		assertNotNull(user2);
		System.out.println(user2);
	}
	
	@Test
	public void createUserTestN() {
		run(20, "createUserTest", () -> createUserTest());
		dump();
	}


	@Test
	public void createUserAndGetTest() {
		String username = "tdd-" + UUID.randomUUID().toString();
		User user = new User().withUsername(username).withEmail(username + "@test.org")
				.withPassword(("Pass123!!-" + username).getBytes())
				.withAddress(new Address().withCountry("USA").withCity("NY").withPostalCode("12345"));
		URI uri = client.createUser(user, null);
		assertNotNull(uri);
		String id = UriUtils.extractId(uri);
		assertNotNull(id);
		assertFalse(id.isEmpty());
		User user2 = client.getUser(id, null);
		assertNotNull(user2);
		assertEquals(id, user2.getId());
		assertEquals(user.getUsername(), user2.getUsername());
	}

	@Test
	public void updateExistingUserTest() {
		String username = TEST_USER;
		User user = client.getUser(username, null);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		user.getAddress().setCity("City-" + UUID.randomUUID().toString());
		user.setMobilePhone(new Phone("90000" + new Random().nextInt(9999)));
		client.updateUser(user, null);
		User user2 = client.getUser(username, null);
		assertNotNull(user2);
		assertEquals(username, user2.getUsername());
		assertEquals(user.getAddress().getCity(), user2.getAddress().getCity());
		assertEquals(user.getMobilePhone(), user2.getMobilePhone());

	}

	@Test
	public void updateExistingUserPartialTest() {
		String username = TEST_USER;
		User user = client.getUser(username, null);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		Address address = new Address();
		user.setAddress(address);
		address.setLine1("Street " + UUID.randomUUID().toString());
		address.setCity("City-" + UUID.randomUUID().toString());
		user.setWebsite("http://website.test.org");
		user.setSocial("http://social.test.org");
		client.updateUser(user, null);
		User user2 = client.getUser(username, null);
		assertNotNull(user2);
		assertEquals(username, user2.getUsername());
		assertEquals(user.getWebsite(), user2.getWebsite());
		assertEquals(user.getProfile(), user2.getProfile());
		assertEquals(user.getAddress().getCity(), user2.getAddress().getCity());
		assertEquals(user.getAddress().getLine1(), user2.getAddress().getLine1());

		user.setWebsite("http://website2.test.org");
		user.setProfile(null);
		user.getAddress().setLine1(null);
		user.getAddress().setCity("City-" + UUID.randomUUID().toString());
		client.updateUser(user, null);
		User user3 = client.getUser(username, null);
		assertNotNull(user3);
		assertEquals(username, user2.getUsername());
		assertEquals(user.getWebsite(), user3.getWebsite());
		assertEquals(user2.getProfile(), user3.getProfile());
		assertEquals(user.getAddress().getCity(), user3.getAddress().getCity());
		assertEquals(user2.getAddress().getLine1(), user3.getAddress().getLine1());
	}

	@Test
	public void createGroupTest() {
		String groupName = "group-" + UUID.randomUUID().toString();
		Group group = new Group().withName(groupName).withDescription("Description of Group:" + groupName);
		URI uri = client.createGroup(group, null);
		assertNotNull(uri);
		client.deleteGroup(groupName, null);
	}

	@Test
	public void createGroupAndGetTest() {
		String groupName = "group-" + UUID.randomUUID().toString();
		Group group = new Group().withName(groupName).withDescription("Description of Group:" + groupName);
		URI uri = client.createGroup(group, null);
		assertNotNull(uri);
		String groupName2 = UriUtils.extractId(uri);
		assertNotNull(groupName2);
		assertFalse(groupName2.isEmpty());
		assertEquals(groupName, groupName2);
		Group group2 = client.getGroup(groupName2, null);
		assertNotNull(group2);
		assertEquals(groupName2, group2.getName());
		client.deleteGroup(groupName2, null);
		try {
			client.getGroup(groupName2, null);
			fail();
		} catch (RuntimeException e) {
		}
	}

	@Test
	public void updateGroupTest() {
		String groupName = "group-" + UUID.randomUUID().toString();
		Group group = new Group().withName(groupName).withDescription("Description of Group:" + groupName);
		URI uri = client.createGroup(group, null);
		assertNotNull(uri);
		Group group2 = client.getGroup(groupName, null);
		assertNotNull(group2);
		assertEquals(groupName, group2.getName());
		group2.setDescription("Other Description");
		client.updateGroup(group2, null);
		Group group3 = client.getGroup(groupName, null);
		assertNotNull(group3);
		assertEquals(group2.getDescription(), group3.getDescription());
		client.deleteGroup(groupName, null);
		try {
			client.getGroup(groupName, null);
			fail();
		} catch (RuntimeException e) {
		}
	}

	@Test
	public void groupMembershipTest() {
		String groupName = "group-" + UUID.randomUUID().toString();
		Group group = new Group().withName(groupName).withDescription("Description of Group:" + groupName);
		URI uri = client.createGroup(group, null);
		assertNotNull(uri);
		String uuid = UriUtils.extractId(uri);
		Group group2 = client.getGroup(uuid, null);
		assertNotNull(group2);
		String userId = TEST_USER;
		client.addToGroup(userId, uuid, null);
		Page<Member> members = client.listGroupMembers(uuid, null, null, null);
		assertNotNull(members);
		assertNotNull(members.getContent());
		assertFalse(members.getContent().isEmpty());
		assertEquals(1, members.getContent().size());
		assertNotNull(members.getContent().get(0));
		assertNotNull(members.getContent().get(0).getUser());
		assertEquals(userId, members.getContent().get(0).getUser().getId());
		client.removeFromGroup(userId, uuid, null);
		Page<Member> members2 = client.listGroupMembers(uuid, null, null, null);
		assertNotNull(members2);
		assertNotNull(members.getContent());
		assertTrue(members2.getContent().isEmpty());
		client.deleteGroup(uuid, null);
		try {
			client.getGroup(uuid, null);
			fail();
		} catch (RuntimeException e) {
		}
	}

	@Test
	public void groupMembershipWithPlusTest() {
		String groupName = "group-" + UUID.randomUUID().toString();
		Group group = new Group().withName(groupName).withDescription("Description of Group:" + groupName);
		URI uri = client.createGroup(group, null);
		assertNotNull(uri);
		String uuid = UriUtils.extractId(uri);
		Group group2 = client.getGroup(uuid, null);
		assertNotNull(group2);
		String userId = TEST_USERX1;
		client.addToGroup(userId, uuid, null);
		Page<Member> members = client.listGroupMembers(uuid, null, null, null);
		assertNotNull(members);
		assertNotNull(members.getContent());
		assertFalse(members.getContent().isEmpty());
		assertEquals(1, members.getContent().size());
		assertNotNull(members.getContent().get(0));
		assertNotNull(members.getContent().get(0).getUser());
		assertEquals(userId, members.getContent().get(0).getUser().getId());
		client.removeFromGroup(userId, uuid, null);
		Page<Member> members2 = client.listGroupMembers(uuid, null, null, null);
		assertNotNull(members2);
		assertNotNull(members.getContent());
		assertTrue(members2.getContent().isEmpty());
		client.deleteGroup(uuid, null);
		try {
			client.getGroup(uuid, null);
			fail();
		} catch (RuntimeException e) {
		}
	}

	@Test
	public void groupContactsTest() {
		String groupName = "group-" + UUID.randomUUID().toString();
		Group group = new Group().withName(groupName).withDescription("Description of Group:" + groupName)
				.withType(GroupType.CONTACTS).withOwner(TEST_USER2);
		URI uri = client.createGroup(group, null);
		assertNotNull(uri);
		String id = UriUtils.extractId(uri);
		Group group2 = client.getGroup(id, null);
		assertNotNull(group2);
		GroupFilter filter = new GroupFilter();
		filter.setType(GroupType.CONTACTS);
		Page<Group> groups = client.listGroups(null, filter, null);
		assertFalse(groups.getNumberOfElements()==0);
		assertFalse(groups.getContent().isEmpty());
		client.addToGroup(TEST_USER, id, null);
		Page<Member> members = client.listGroupMembers(id, null, null, null);
		assertNotNull(members);
		assertNotNull(members.getContent());
		assertFalse(members.getContent().isEmpty());
		assertEquals(1, members.getContent().size());
		assertNotNull(members.getContent().get(0));
		assertEquals(TEST_USER, members.getContent().get(0).getUser().getId());

		client.removeFromGroup(TEST_USER, id, null);
		Page<Member> members2 = client.listGroupMembers(id, null, null, null);
		assertNotNull(members2);
		assertNotNull(members.getContent());
		assertTrue(members2.getContent().isEmpty());
		client.deleteGroup(id, null);
		try {
			client.getGroup(groupName, null);
			fail();
		} catch (RuntimeException e) {
		}
	}

	@Test
	public void invitationNoSendMailTest() {
		invitationTest(InvitationType.USER, false, false);
	}

	@Test
	public void invitationSendMailOnInviteTest() {
		invitationTest(InvitationType.USER, true, false);
	}

	@Test
	public void invitationSendMailOnTokenFetchTest() {
		invitationTest(InvitationType.USER, false, true);
	}

	public void invitationTest(InvitationType type, Boolean sendMail, Boolean sendMail2) {
		Invitation invitation = new Invitation();
		invitation.setInvitee(TEST_USER);
		invitation.setOwner(TEST_USER2);
		invitation.setType(type);
		invitation.setDescription("Test invitation: " + UUID.randomUUID());
		URI uri = client.invite(invitation, sendMail, null);
		assertNotNull(uri);
		String id = UriUtils.extractId(uri);
		assertNotNull(id);
		Invitation invitation2 = client.getInvitation(id, null);
		assertNotNull(invitation2);
		assertEquals(id, invitation2.getUuid());
		URI tokenUri = client.getInvitationToken(id, sendMail, null);
		assertNotNull(tokenUri);
		assertTrue(tokenUri.toString().indexOf("?token=") > 0);
		String token = tokenUri.toString().substring(tokenUri.toString().indexOf("=") + 1);
		System.out.println(token + " " + tokenUri);
		assertNotEquals("null", token);
	}
	
	@Test
	public void listInvitationsTest() {
		//InvitationFilter filter = new InvitationFilter();
		Page<Invitation> invitations = client.listInvitations(null, null, null);
		assertNotNull(invitations);
		assertNotNull(invitations.getContent());
		assertFalse(invitations.getContent().isEmpty());
		for (Invitation invitation: invitations) {
			System.out.println(invitation);			
		}
	}

}
