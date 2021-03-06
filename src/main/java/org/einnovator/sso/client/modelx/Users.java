package org.einnovator.sso.client.modelx;

import java.util.List;

import org.einnovator.util.model.ObjectBase;
import org.einnovator.util.model.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Utility collection of {@code Users}s.
 * 
 * @see org.einnovator.sso.client.manager.InvitationManager
 * @see org.einnovator.sso.client.model.User
 * @author support@einnovator.org
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Users extends ObjectBase {

	protected List<String> users;

	protected Boolean member;

	/**
	 * Create instance of {@code Users}.
	 *
	 */
	public Users() {
	}

	
	/**
	 * Get the value of property {@code users}.
	 *
	 * @return the users
	 */
	public List<String> getUsers() {
		return users;
	}


	/**
	 * Set the value of property {@code users}.
	 *
	 * @param users the users
	 */
	public void setUsers(List<String> users) {
		this.users = users;
	}

	
	/**
	 * Get the value of property {@code member}.
	 *
	 * @return the member
	 */
	public Boolean getMember() {
		return member;
	}


	/**
	 * Set the value of property {@code member}.
	 *
	 * @param member the value of property member
	 */
	public void setMember(Boolean member) {
		this.member = member;
	}


	@Override
	public ToStringCreator toString(ToStringCreator creator) {
		return super.toString(creator)
			.append("users", users)
			.append("member", member)
			;
	}
	
}
