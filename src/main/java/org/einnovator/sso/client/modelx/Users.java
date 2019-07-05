package org.einnovator.sso.client.modelx;

import java.util.List;

import org.einnovator.util.model.ObjectBase;
import org.einnovator.util.model.ToStringCreator;

public class Users extends ObjectBase {

	private List<String> users;

	private Boolean member;

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
	 * @param users the users to set
	 */
	public void setUsers(List<String> users) {
		this.users = users;
	}


	/**
	 * Get the value of property {@code members}.
	 *
	 * @return the members
	 */
	public Boolean getMember() {
		return member;
	}


	/**
	 * Set the value of property {@code members}.
	 *
	 * @param members the members to set
	 */
	public void setMembers(Boolean member) {
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
