package org.einnovator.sso.client.model;

import java.util.List;

import org.einnovator.util.model.EntityBase;

public class RequestBuilder extends EntityBase {

	private RequestType type;
	
	private RequestStatus status;

	private User user;

	private Group group;

	private List<Role> roles;

	private User initiator;
	
	public RequestBuilder() {
	}

	public RequestBuilder type(RequestType type) {
		this.type = type;
		return this;
	}

	public RequestBuilder status(RequestStatus status) {
		this.status = status;
		return this;
	}

	public RequestBuilder user(User user) {
		this.user = user;
		return this;
	}

	public RequestBuilder group(Group group) {
		this.group = group;
		return this;
	}

	public RequestBuilder roles(List<Role> roles) {
		this.roles = roles;
		return this;
	}
	
	public RequestBuilder initiator(User initiator) {
		this.initiator = initiator;
		return this;
	}

	public Request build() {
		Request request = new Request();
		request.setType(type);
		request.setStatus(status);
		request.setUser(user);
		request.setGroup(group);
		request.setInitiator(initiator);
		request.setRoles(roles);
		return request;
	}

}
