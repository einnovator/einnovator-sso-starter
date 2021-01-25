/**
 * 
 */
package com.einnovator.sso.client.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;

import org.einnovator.sso.client.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 */
public class RoleTests {

	private static final String ROLE_MANAGER = "MANAGER";
	private static final String ROLE_AUDITOR = "AUDITOR";

	@Test
	public void test() {
		String groupId="1";
		
		
		assertTrue(Role.equals(ROLE_MANAGER, Role.normalize(ROLE_MANAGER)));

		GrantedAuthority[] authorities_ = new GrantedAuthority[] {
		Role.makeGrantedAuthority(ROLE_MANAGER, groupId),
		Role.makeGrantedAuthority(ROLE_AUDITOR, groupId),
		Role.makeGrantedAuthority(Role.ROLE_USER, groupId)
		};
		Collection<? extends GrantedAuthority> authorities = Arrays.asList(authorities_);
			 
		assertTrue(Role.hasRoleInGroup(ROLE_MANAGER, groupId, authorities));
		assertTrue(Role.hasRoleInGroup(ROLE_AUDITOR, groupId, authorities));
		assertTrue(Role.hasRoleInGroup(Role.ROLE_USER, groupId, authorities));
		assertFalse(Role.hasRoleInGroup(Role.ROLE_ADMIN, groupId, authorities));
		assertTrue(Role.hasAnyRoleInGroup(groupId, authorities, ROLE_MANAGER, ROLE_AUDITOR, Role.ROLE_ADMIN));

	}

}
