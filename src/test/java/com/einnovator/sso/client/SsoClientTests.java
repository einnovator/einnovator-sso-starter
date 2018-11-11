package com.einnovator.sso.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.Random;
import java.util.UUID;

import org.einnovator.util.UriUtils;
import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.SsoClientConfiguration;
import org.einnovator.sso.client.SsoClientSecurityConfigurer;
import org.einnovator.sso.client.model.Address;
import org.einnovator.sso.client.model.AddressBuilder;
import org.einnovator.sso.client.model.Group;
import org.einnovator.sso.client.model.GroupBuilder;
import org.einnovator.sso.client.model.GroupType;
import org.einnovator.sso.client.model.Invitation;
import org.einnovator.sso.client.model.InvitationType;
import org.einnovator.sso.client.model.Member;
import org.einnovator.sso.client.model.Phone;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.model.UserBuilder;
import org.einnovator.sso.client.modelx.GroupFilter;
import org.einnovator.sso.client.modelx.UserFilter;
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

import static com.einnovator.sso.client.Profiler.run;
import static com.einnovator.sso.client.Profiler.dump;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SsoClientTests.TestConfig.class,
		SsoClientSecurityConfigurer.class }, webEnvironment = WebEnvironment.MOCK)
@EnableCaching
@TestPropertySource(properties = { "sso.server=http://localhost:28081/auth", "sso.server_=http://localhost:28083/" })
public class SsoClientTests {

	@Autowired
	private SsoClient client;

	@SuppressWarnings("unused")
	@Autowired
	private SsoClientConfiguration config;

	private final String TEST_USER = "tdd@einnovator.org";
	private final String TEST_USER2 = "jsimao71@gmail.com";
	@SuppressWarnings("unused")
	private final String TEST_USER3 = "jobs@einnovator.org";

	private final String TEST_PASSWORD = "Einnovator123!!";
	private final String TEST_USERX1 = "info+100@einnovator.org";

	public static final String CLIENT_ID = "greenfence";
	public static final String CLIENT_SECRET = "greenfence$123";

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
		Page<User> users = client.listUsers(null, null);
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
		Page<User> users = client.listUsers(filter, null);
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
		String username = TEST_USER2;
		User user = client.getUser(username);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
	}

	@Test
	public void createUserTest() {
		String username = "tdd-" + UUID.randomUUID().toString();
		User user = new UserBuilder().username(username).email(username + "@test.org")
				.password(("Pass123!!-" + username).getBytes()).build();
		URI uri = run("SsoClientTests:createUserTest:createUser", () -> client.createUser(user));
		assertNotNull(uri);
		String id = UriUtils.extractId(uri);
		User user2 = run("SsoClientTests:createUserTest:getUser", () -> client.getUser(id));
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
		User user = new UserBuilder().username(username).email(username + "@test.org")
				.password(("Pass123!!-" + username).getBytes())
				.address(new AddressBuilder().country("USA").city("NY").postalCode("12345").build()).build();
		URI uri = client.createUser(user);
		assertNotNull(uri);
		String id = UriUtils.extractId(uri);
		assertNotNull(id);
		assertFalse(id.isEmpty());
		User user2 = client.getUser(id);
		assertNotNull(user2);
		assertEquals(id, user2.getId());
		assertEquals(user.getUsername(), user2.getUsername());
	}

	@Test
	public void updateExistingUserTest() {
		String username = TEST_USER;
		User user = client.getUser(username);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		user.getAddress().setCity("City-" + UUID.randomUUID().toString());
		user.setMobilePhone(new Phone("90000" + new Random().nextInt(9999)));
		client.updateUser(user);
		User user2 = client.getUser(username);
		assertNotNull(user2);
		assertEquals(username, user2.getUsername());
		assertEquals(user.getAddress().getCity(), user2.getAddress().getCity());
		assertEquals(user.getMobilePhone(), user2.getMobilePhone());

	}

	@Test
	public void updateExistingUserPartialTest() {
		String username = TEST_USER;
		User user = client.getUser(username);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		Address address = new Address();
		user.setAddress(address);
		address.setLine1("Street " + UUID.randomUUID().toString());
		address.setCity("City-" + UUID.randomUUID().toString());
		user.setWebsite("http://website.test.org");
		user.setSocial("http://social.test.org");
		client.updateUser(user);
		User user2 = client.getUser(username);
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
		client.updateUser(user);
		User user3 = client.getUser(username);
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
		Group group = new GroupBuilder().name(groupName).description("Description of Group:" + groupName).build();
		URI uri = client.createGroup(group);
		assertNotNull(uri);
		client.deleteGroup(groupName);
	}

	@Test
	public void createGroupAndGetTest() {
		String groupName = "group-" + UUID.randomUUID().toString();
		Group group = new GroupBuilder().name(groupName).description("Description of Group:" + groupName).build();
		URI uri = client.createGroup(group);
		assertNotNull(uri);
		String groupName2 = UriUtils.extractId(uri);
		assertNotNull(groupName2);
		assertFalse(groupName2.isEmpty());
		assertEquals(groupName, groupName2);
		Group group2 = client.getGroup(groupName2);
		assertNotNull(group2);
		assertEquals(groupName2, group2.getName());
		client.deleteGroup(groupName2);
		try {
			client.getGroup(groupName2);
			fail();
		} catch (RuntimeException e) {
		}
	}

	@Test
	public void updateGroupTest() {
		String groupName = "group-" + UUID.randomUUID().toString();
		Group group = new GroupBuilder().name(groupName).description("Description of Group:" + groupName).build();
		URI uri = client.createGroup(group);
		assertNotNull(uri);
		Group group2 = client.getGroup(groupName);
		assertNotNull(group2);
		assertEquals(groupName, group2.getName());
		group2.setDescription("Other Description");
		client.updateGroup(group2);
		Group group3 = client.getGroup(groupName);
		assertNotNull(group3);
		assertEquals(group2.getDescription(), group3.getDescription());
		client.deleteGroup(groupName);
		try {
			client.getGroup(groupName);
			fail();
		} catch (RuntimeException e) {
		}
	}

	@Test
	public void groupMembershipTest() {
		String groupName = "group-" + UUID.randomUUID().toString();
		Group group = new GroupBuilder().name(groupName).description("Description of Group:" + groupName).build();
		URI uri = client.createGroup(group);
		assertNotNull(uri);
		String uuid = UriUtils.extractId(uri);
		Group group2 = client.getGroup(uuid);
		assertNotNull(group2);
		String userId = TEST_USER;
		client.addToGroup(userId, uuid);
		Page<Member> members = client.listGroupMembers(uuid, null, null);
		assertNotNull(members);
		assertNotNull(members.getContent());
		assertFalse(members.getContent().isEmpty());
		assertEquals(1, members.getContent().size());
		assertNotNull(members.getContent().get(0));
		assertNotNull(members.getContent().get(0).getUser());
		assertEquals(userId, members.getContent().get(0).getUser().getId());
		client.removeFromGroup(userId, uuid);
		Page<Member> members2 = client.listGroupMembers(uuid, null, null);
		assertNotNull(members2);
		assertNotNull(members.getContent());
		assertTrue(members2.getContent().isEmpty());
		client.deleteGroup(uuid);
		try {
			client.getGroup(uuid);
			fail();
		} catch (RuntimeException e) {
		}
	}

	@Test
	public void groupMembershipWithPlusTest() {
		String groupName = "group-" + UUID.randomUUID().toString();
		Group group = new GroupBuilder().name(groupName).description("Description of Group:" + groupName).build();
		URI uri = client.createGroup(group);
		assertNotNull(uri);
		String uuid = UriUtils.extractId(uri);
		Group group2 = client.getGroup(uuid);
		assertNotNull(group2);
		String userId = TEST_USERX1;
		client.addToGroup(userId, uuid);
		Page<Member> members = client.listGroupMembers(uuid, null, null);
		assertNotNull(members);
		assertNotNull(members.getContent());
		assertFalse(members.getContent().isEmpty());
		assertEquals(1, members.getContent().size());
		assertNotNull(members.getContent().get(0));
		assertNotNull(members.getContent().get(0).getUser());
		assertEquals(userId, members.getContent().get(0).getUser().getId());
		client.removeFromGroup(userId, uuid);
		Page<Member> members2 = client.listGroupMembers(uuid, null, null);
		assertNotNull(members2);
		assertNotNull(members.getContent());
		assertTrue(members2.getContent().isEmpty());
		client.deleteGroup(uuid);
		try {
			client.getGroup(uuid);
			fail();
		} catch (RuntimeException e) {
		}
	}

	@Test
	public void groupContactsTest() {
		String groupName = "group-" + UUID.randomUUID().toString();
		Group group = new GroupBuilder().name(groupName).description("Description of Group:" + groupName)
				.type(GroupType.CONTACTS).owner(TEST_USER2).build();
		URI uri = client.createGroup(group);
		assertNotNull(uri);
		String id = UriUtils.extractId(uri);
		Group group2 = client.getGroup(id);
		assertNotNull(group2);
		GroupFilter filter = new GroupFilter();
		filter.setType(GroupType.CONTACTS);
		Page<Group> groups = client.listGroups(null, filter);
		assertFalse(groups.getNumberOfElements()==0);
		assertFalse(groups.getContent().isEmpty());
		client.addToGroup(TEST_USER, id);
		Page<Member> members = client.listGroupMembers(id, null, null);
		assertNotNull(members);
		assertNotNull(members.getContent());
		assertFalse(members.getContent().isEmpty());
		assertEquals(1, members.getContent().size());
		assertNotNull(members.getContent().get(0));
		assertEquals(TEST_USER, members.getContent().get(0).getUser().getId());

		client.removeFromGroup(TEST_USER, id);
		Page<Member> members2 = client.listGroupMembers(id, null, null);
		assertNotNull(members2);
		assertNotNull(members.getContent());
		assertTrue(members2.getContent().isEmpty());
		client.deleteGroup(id);
		try {
			client.getGroup(groupName);
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
		URI uri = client.invite(invitation, sendMail, null /* redirectUri */);
		assertNotNull(uri);
		String id = UriUtils.extractId(uri);
		assertNotNull(id);
		Invitation invitation2 = client.getInvitation(id);
		assertNotNull(invitation2);
		assertEquals(id, invitation2.getUuid());
		URI tokenUri = client.getInvitationToken(id, sendMail);
		assertNotNull(tokenUri);
		assertTrue(tokenUri.toString().indexOf("?token=") > 0);
		String token = tokenUri.toString().substring(tokenUri.toString().indexOf("=") + 1);
		System.out.println(token + " " + tokenUri);
		assertNotEquals("null", token);
	}
	
	@Test
	public void listInvitationsTest() {
		//InvitationFilter filter = new InvitationFilter();
		Page<Invitation> invitations = client.listInvitations(null, null);
		assertNotNull(invitations);
		assertNotNull(invitations.getContent());
		assertFalse(invitations.getContent().isEmpty());
		for (Invitation invitation: invitations) {
			System.out.println(invitation);			
		}
	}

}