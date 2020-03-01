/**
 * 
 */
package org.einnovator.sso.client.model;

import org.einnovator.util.model.ObjectBase;
import org.einnovator.util.model.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;



/**
 * A {@code RoleBinding}.
 *
 * @author support@einnovator.org
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleBinding extends ObjectBase {

	private User user;
	
	private Group group;

	/**
	 * Create instance of {@code RoleBinding}.
	 *
	 */
	public RoleBinding() {
	}
	
	/**
	 * Create instance of {@code RoleBinding}.
	 *
	 * @param user a {@code User}
	 */
	public RoleBinding(User user) {
		this.user = user;
	}

	/**
	 * Create instance of {@code RoleBinding}.
	 * 
	 * @param group a {@code Group}
	 */
	public RoleBinding(Group group) {
		this.group = group;
	}
	
	/**
	 * Get the value of property {@code user}.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Set the value of property {@code user}.
	 *
	 * @param user the value of property user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Get the value of property {@code group}.
	 *
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * Set the value of property {@code group}.
	 *
	 * @param group the value of property group
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
				.append("user", user)
				.append("group", group)
				;
	}
}
