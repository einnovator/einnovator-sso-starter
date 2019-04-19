package org.einnovator.sso.client.model;

import java.util.ArrayList;
import java.util.List;

import org.einnovator.util.model.EntityBase;
import org.einnovator.util.model.ToStringCreator;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request extends EntityBase {

	private RequestType type;
	
	private RequestStatus status;

	private User user;

	private Group group;

	private List<Role> roles;

	private User initiator;
	
	
	/**
	 * Create instance of {@code Request}.
	 *
	 */
	public Request() {
	}

	/**
	 * Create instance of {@code Request} from other {@code Object}.
	 *
	 */
	public Request(Object obj) {
		super(obj);
	}
	
	/**
	 * Get the value of property {@code type}.
	 *
	 * @return the type
	 */
	public RequestType getType() {
		return type;
	}

	/**
	 * Set the value of property {@code type}.
	 *
	 * @param type the type to set
	 */
	public void setType(RequestType type) {
		this.type = type;
	}

	/**
	 * Get the value of property {@code status}.
	 *
	 * @return the status
	 */
	public RequestStatus getStatus() {
		return status;
	}

	/**
	 * Set the value of property {@code status}.
	 *
	 * @param status the status to set
	 */
	public void setStatus(RequestStatus status) {
		this.status = status;
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
	 * @param user the user to set
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
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * Get the value of property {@code roles}.
	 *
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * Set the value of property {@code roles}.
	 *
	 * @param roles the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * Get the value of property {@code initiator}.
	 *
	 * @return the initiator
	 */
	public User getInitiator() {
		return initiator;
	}

	/**
	 * Set the value of property {@code initiator}.
	 *
	 * @param initiator the initiator to set
	 */
	public void setInitiator(User initiator) {
		this.initiator = initiator;
	}

	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
			.append("type", type)
			.append("status", status)
			.append("user", user)
			.append("group", group)
			.append("roles", roles)
			.append("initiator", initiator)
			;
	}
	
	public static List<User> getUsers(List<Request> requests) {
		if (requests==null) {
			return null;
		}
		List<User> users = new ArrayList<>();
		for (Request request: requests) {
			User user = request.getUser();
			if (user!=null) {
				users.add(user);
			}
		}
		return users;
	}

	public static List<User> getUsers(Page<Request> page) {
		if (page==null) {
			return null;
		}
		return getUsers(page.getContent());
	}

}
