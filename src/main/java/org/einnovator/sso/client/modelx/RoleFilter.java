package org.einnovator.sso.client.modelx;

import org.einnovator.sso.client.model.RoleType;
import org.einnovator.util.model.ToStringCreator;

public class RoleFilter extends RoleOptions {

	private String q;
	
	private String name;
	
	private String group;
	
	private String user;
	
	private RoleType type;

	/**
	 * Create instance of {@code RoleFilter}.
	 *
	 */
	public RoleFilter() {
	}
	
	/**
	 * Get the value of property {@code q}.
	 *
	 * @return the q
	 */
	public String getQ() {
		return q;
	}

	/**
	 * Set the value of property {@code q}.
	 *
	 * @param q the q to set
	 */
	public void setQ(String q) {
		this.q = q;
	}


	/**
	 * Get the value of property {@code name}.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the value of property {@code name}.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Get the value of property {@code group}.
	 *
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * Set the value of property {@code group}.
	 *
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Get the value of property {@code user}.
	 *
	 * @return the user
	 */
	public String getUser() {
		return user;
	}


	/**
	 * Set the value of property {@code user}.
	 *
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}


	/**
	 * Get the value of property {@code type}.
	 *
	 * @return the type
	 */
	public RoleType getType() {
		return type;
	}


	/**
	 * Set the value of property {@code type}.
	 *
	 * @param type the type to set
	 */
	public void setType(RoleType type) {
		this.type = type;
	}

	//
	

	/**
	 * Set the value of property {@code q}.
	 *
	 * @param q the q to with
	 * @return this
	 */
	public RoleFilter withQ(String q) {
		this.q = q;
		return this;
	}

	/**
	 * Set the value of property {@code name}.
	 *
	 * @param name the name to with
	 * @return this
	 */
	public RoleFilter withName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Set the value of property {@code group}.
	 *
	 * @param group the group to with
	 * @return this
	 */
	public RoleFilter withGroup(String group) {
		this.group = group;
		return this;
	}

	/**
	 * Set the value of property {@code user}.
	 *
	 * @param user the user to with
	 * @return this
	 */
	public RoleFilter withUser(String user) {
		this.user = user;
		return this;
	}


	/**
	 * Set the value of property {@code type}.
	 *
	 * @param type the type to with
	 * @return this
	 */
	public RoleFilter withType(RoleType type) {
		this.type = type;
		return this;
	}



	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
				.append("q", q)
				.append("group", group)
				.append("name", name)
				.append("user", user)
				.append("type", type)
			;
	}
	
	
	
}
