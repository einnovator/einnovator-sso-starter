package org.einnovator.sso.client.modelx;

import org.einnovator.sso.client.model.RequestStatus;
import org.einnovator.util.model.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestFilter extends RequestOptions {
	
	private RequestStatus status;

	private String user;

	private String group;
	
	public RequestFilter() {
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


	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
				.append("status", status)
				.append("user", user)
				.append("group", group)
				;
	}

}
