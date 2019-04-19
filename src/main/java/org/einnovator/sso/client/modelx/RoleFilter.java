package org.einnovator.sso.client.modelx;

import org.einnovator.sso.client.model.RoleType;
import org.einnovator.util.model.ToStringCreator;

public class RoleFilter extends RoleOptions {

	private String q;
	
	private String name;
	
	private String group;
	
	private Boolean inherited;
	
	private Boolean builtin;
	
	private Boolean custom;
	
	private Boolean global;

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
	 * Get the value of property {@code inherited}.
	 *
	 * @return the inherited
	 */
	public Boolean getInherited() {
		return inherited;
	}


	/**
	 * Set the value of property {@code inherited}.
	 *
	 * @param inherited the inherited to set
	 */
	public void setInherited(Boolean inherited) {
		this.inherited = inherited;
	}

	/**
	 * Get the value of property {@code builtin}.
	 *
	 * @return the builtin
	 */
	public Boolean getBuiltin() {
		return builtin;
	}


	/**
	 * Set the value of property {@code builtin}.
	 *
	 * @param builtin the builtin to set
	 */
	public void setBuiltin(Boolean builtin) {
		this.builtin = builtin;
	}


	/**
	 * Get the value of property {@code custom}.
	 *
	 * @return the custom
	 */
	public Boolean getCustom() {
		return custom;
	}


	/**
	 * Set the value of property {@code custom}.
	 *
	 * @param custom the custom to set
	 */
	public void setCustom(Boolean custom) {
		this.custom = custom;
	}


	/**
	 * Get the value of property {@code global}.
	 *
	 * @return the global
	 */
	public Boolean getGlobal() {
		return global;
	}


	/**
	 * Set the value of property {@code global}.
	 *
	 * @param global the global to set
	 */
	public void setGlobal(Boolean global) {
		this.global = global;
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


	@Deprecated
	public String getOrgId() {
		return group;
	}

	@Deprecated
	public void setOrgId(String orgId) {
		this.group = orgId;
	}

	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
				.append("q", q)
				.append("group", group)
				.append("name", name)
				.append("inherited", inherited)
				.append("builtin", builtin)
				.append("global", global)
				.append("custom", custom)
				.append("user", user)
				.append("type", type)
			;
	}
	
	
	
}
