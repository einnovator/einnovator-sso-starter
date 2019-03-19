package org.einnovator.sso.client.modelx;

import org.einnovator.sso.client.model.RequestStatus;

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

	public RequestStatus getStatus() {
		return status;
	}


	public void setStatus(RequestStatus status) {
		this.status = status;
	}


	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	
	public String find() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " ["
				+ (user != null ? "user=" + user + ", " : "")
				+ (group != null ? "group=" + group + ", " : "")
				+ "]";
	} 
	
}
