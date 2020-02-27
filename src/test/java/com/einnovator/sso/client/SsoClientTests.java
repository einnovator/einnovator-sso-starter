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

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.config.SsoClientConfiguration;
import org.einnovator.sso.client.config.SsoClientSecurityConfigurer;
import org.einnovator.sso.client.model.Group;
import org.einnovator.sso.client.model.Invitation;
import org.einnovator.sso.client.model.InvitationType;
import org.einnovator.sso.client.model.Member;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.InvitationOptions;
import org.einnovator.sso.client.modelx.UserFilter;
import org.einnovator.util.PageUtil;
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
		assertNotNull(token);
		System.out.println(token);
	}

	//
	// User
	//
	
	@Test
	public void listUsersTest() {
		Page<User> users = client.listUsers(null, null, null);
		assertNotNull(users);
		assertNotNull(users.getContent());
		assertFalse(users.getNumberOfElements()==0);
		assertFalse(users.getContent().isEmpty());
		System.out.println(PageUtil.toString(users));
	}

	@Test
	public void listUsersWithFilterTest() {
		User user0 = getRandomUser();
		String username = user0.getUsername();
		assertNotNull(username);
		assertFalse(username.isEmpty());
		String q = getRandomSubstring(username);
		Page<User> users = client.listUsers(new UserFilter().withQ(q), null, null);
		assertNotNull(users);
		assertNotNull(users.getContent());
		assertFalse(users.getNumberOfElements()==0);
		assertFalse(users.getContent().isEmpty());
		for (User user : users) {
			assertTrue(user.getUsername().contains(q) || user.getDisplayName().toLowerCase().contains(q));
		}
		System.out.println(PageUtil.toString(users));

	}

	@Test
	public void getExistingUserTest() {
		User user0 = getRandomUser();
		User user = client.getUser(user0.getUsername(), null, null);
		assertNotNull(user);
		assertEquals(user0.getUsername(), user.getUsername());
		user = client.getUser(user0.getUuid(), null, null);
		assertNotNull(user);
		assertEquals(user0.getUuid(), user.getUuid());
		user = client.getUser(user0.getEmail(), null, null);
		assertNotNull(user);
		assertEquals(user0.getEmail(), user.getEmail());
	}

	private User getRandomUser() {
		Page<User> users = client.listUsers(null, null, null);
		assertNotNull(users);
		assertNotNull(users.getContent());
		assertFalse(users.getContent().isEmpty());
		return users.getContent().get(new Random().nextInt(users.getContent().size()));
	}
	
	public static final String getRandomSubstring(String s) {
		if (s==null) {
			return null;
		}
		if (s.length()<=1) {
			return s;
		}
		int i0 = new Random().nextInt(s.length());
		int i1 = new Random().nextInt(s.length());
		if (i1<i0) {
			int i = i0;
			i0 = i1;
			i1 = i;
		} else if (i0==i1) {
			if (i0==0) {
				i1 = 1;
			} else if (i0==s.length()-1) {
				i0 = s.length()-2;
			}
		}
		return s.substring(i0, i1);
	}
	
	@Test
	public void createAndDeleteUserTest() {
		String username = "tdd-" + UUID.randomUUID().toString();
		User user = new User()
				.withUsername(username)
				.withEmail(username + "@test.org")
				.withPassword(("Pass123!!-" + username).getBytes())
				.withAddress(new Address().withCountry("USA").withCity("NY").withPostalCode("12345"));
				;
		URI uri = client.createUser(user, null, null);
		assertNotNull(uri);
		String id = UriUtils.extractId(uri);
		assertNotNull(id);
		assertFalse(id.isEmpty());
		User user2 = client.getUser(id, null, null);
		assertNotNull(user2);
		assertEquals(id, user2.getId());
		assertEquals(user.getUsername(), user2.getUsername());

		System.out.println(user2);
		client.deleteUser(id, null, null);
		try {
			client.getUser(id, null, null);
			fail();
		} catch (RuntimeException e) {
		}
	}


	@Test
	public void updateExistingUserTest() {
		String username = TEST_USER;
		User user = client.getUser(username, null, null);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		user.getAddress().setCity("City-" + UUID.randomUUID().toString());
		user.setMobilePhone(new Phone("90000" + new Random().nextInt(9999)));
		client.updateUser(user, null, null);
		User user2 = client.getUser(username, null, null);
		assertNotNull(user2);
		assertEquals(username, user2.getUsername());
		assertEquals(user.getAddress().getCity(), user2.getAddress().getCity());
		assertEquals(user.getMobilePhone(), user2.getMobilePhone());

	}

	@Test
	public void updateExistingUserPartialTest() {
		String username = TEST_USER;
		User user = client.getUser(username, null, null);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		Address address = new Address();
		user.setAddress(address);
		address.setLine1("Street " + UUID.randomUUID().toString());
		address.setCity("City-" + UUID.randomUUID().toString());
		user.setWebsite("http://website.test.org");
		user.setSocial("http://social.test.org");
		client.updateUser(user, null, null);
		User user2 = client.getUser(username, null, null);
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
		client.updateUser(user, null, null);
		User user3 = client.getUser(username, null, null);
		assertNotNull(user3);
		assertEquals(username, user2.getUsername());
		assertEquals(user.getWebsite(), user3.getWebsite());
		assertEquals(user2.getProfile(), user3.getProfile());
		assertEquals(user.getAddress().getCity(), user3.getAddress().getCity());
		assertEquals(user2.getAddress().getLine1(), user3.getAddress().getLine1());
	}

	
	//
	// Group
	//
	
	@Test
	public void createGroupTest() {
		String groupName = "group-" + UUID.randomUUID().toString();
		Group group = new Group().withName(groupName).withDescription("Description of Group:" + groupName);
		URI uri = client.createGroup(group, null, null);
		assertNotNull(uri);
		client.deleteGroup(groupName, null, null);
	}

	@Test
	public void createGroupAndGetTest() {
		String groupName = "group-" + UUID.randomUUID().toString();
		Group group = new Group().withName(groupName).withDescription("Description of Group:" + groupName);
		URI uri = client.createGroup(group, null, null);
		assertNotNull(uri);
		String groupName2 = UriUtils.extractId(uri);
		assertNotNull(groupName2);
		assertFalse(groupName2.isEmpty());
		assertEquals(groupName, groupName2);
		Group group2 = client.getGroup(groupName2, null, null);
		assertNotNull(group2);
		assertEquals(groupName2, group2.getName());
		client.deleteGroup(groupName2, null, null);
		try {
			client.getGroup(groupName2, null, null);
			fail();
		} catch (RuntimeException e) {
		}
	}

	@Test
	public void updateGroupTest() {
		String groupName = "group-" + UUID.randomUUID().toString();
		Group group = new Group().withName(groupName).withDescription("Description of Group:" + groupName);
		URI uri = client.createGroup(group, null, null);
		assertNotNull(uri);
		Group group2 = client.getGroup(groupName, null, null);
		assertNotNull(group2);
		assertEquals(groupName, group2.getName());
		group2.setDescription("Other Description");
		client.updateGroup(group2, null, null);
		Group group3 = client.getGroup(groupName, null, null);
		assertNotNull(group3);
		assertEquals(group2.getDescription(), group3.getDescription());
		client.deleteGroup(groupName, null, null);
		try {
			client.getGroup(groupName, null, null);
			fail();
		} catch (RuntimeException e) {
		}
	}

	//
	// Members
	//
	
	@Test
	public void groupMembershipTest() {
		String groupName = "group-" + UUID.randomUUID().toString();
		Group group = new Group().withName(groupName).withDescription("Description of Group:" + groupName);
		URI uri = client.createGroup(group, null, null);
		assertNotNull(uri);
		String uuid = UriUtils.extractId(uri);
		Group group2 = client.getGroup(uuid, null, null);
		assertNotNull(group2);
		String userId = TEST_USER;
		client.addMemberToGroup(userId, uuid, null, null);
		Page<Member> members = client.listGroupMembers(uuid, null, null, null);
		assertNotNull(members);
		assertNotNull(members.getContent());
		assertFalse(members.getContent().isEmpty());
		assertEquals(1, members.getContent().size());
		assertNotNull(members.getContent().get(0));
		assertNotNull(members.getContent().get(0).getUser());
		assertEquals(userId, members.getContent().get(0).getUser().getId());
		client.removeMemberFromGroup(userId, uuid, null, null);
		Page<Member> members2 = client.listGroupMembers(uuid, null, null, null);
		assertNotNull(members2);
		assertNotNull(members.getContent());
		assertTrue(members2.getContent().isEmpty());
		client.deleteGroup(uuid, null, null);
		try {
			client.getGroup(uuid, null, null);
			fail();
		} catch (RuntimeException e) {
		}
	}

	@Test
	public void groupMembershipWithPlusTest() {
		String groupName = "group-" + UUID.randomUUID().toString();
		Group group = new Group().withName(groupName).withDescription("Description of Group:" + groupName);
		URI uri = client.createGroup(group, null, null);
		assertNotNull(uri);
		String uuid = UriUtils.extractId(uri);
		Group group2 = client.getGroup(uuid, null, null);
		assertNotNull(group2);
		String userId = TEST_USERX1;
		client.addMemberToGroup(userId, uuid, null, null);
		Page<Member> members = client.listGroupMembers(uuid, null, null, null);
		assertNotNull(members);
		assertNotNull(members.getContent());
		assertFalse(members.getContent().isEmpty());
		assertEquals(1, members.getContent().size());
		assertNotNull(members.getContent().get(0));
		assertNotNull(members.getContent().get(0).getUser());
		assertEquals(userId, members.getContent().get(0).getUser().getId());
		client.removeMemberFromGroup(userId, uuid, null, null);
		Page<Member> members2 = client.listGroupMembers(uuid, null, null, null);
		assertNotNull(members2);
		assertNotNull(members.getContent());
		assertTrue(members2.getContent().isEmpty());
		client.deleteGroup(uuid, null, null);
		try {
			client.getGroup(uuid, null, null);
			fail();
		} catch (RuntimeException e) {
		}
	}

	//
	// Invitation
	//
	
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
		URI uri = client.invite(invitation, new InvitationOptions().withSendMail(sendMail), null);
		assertNotNull(uri);
		String id = UriUtils.extractId(uri);
		assertNotNull(id);
		Invitation invitation2 = client.getInvitation(id, null, null);
		assertNotNull(invitation2);
		assertEquals(id, invitation2.getUuid());
		URI tokenUri = client.getInvitationToken(id, new InvitationOptions().withSendMail(sendMail), null);
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
	
	//
	// Role
	//

	
	//
	// Client
	//

}
