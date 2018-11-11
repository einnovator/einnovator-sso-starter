package org.einnovator.sso.client.model;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class InviteeBuilder {
	
	private InvitationStatus status;

	private String username;

	private Group group;
	
	private Date sendDate;

	private String action;

	private Date responseDate;

	public InviteeBuilder() {
	}
	
	public InviteeBuilder status(InvitationStatus status) {
		this.status = status;
		return this;
	}

	public InviteeBuilder username(String username) {
		this.username = username;
		return this;
	}

	public InviteeBuilder group(Group group) {
		this.group = group;
		return this;
	}

	public InviteeBuilder sendDate(Date sendDate) {
		this.sendDate = sendDate;
		return this;
	}

	public InviteeBuilder action(String action) {
		this.action = action;
		return this;
	}

	public InviteeBuilder responseDate(Date responseDate) {
		this.responseDate = responseDate;
		return this;
	}
	
	public Invitee build() {
		Invitee invitee = new Invitee();
		invitee.setStatus(status);
		invitee.setUsername(username);
		invitee.setGroup(group);
		invitee.setSendDate(sendDate);
		invitee.setAction(action);
		invitee.setResponseDate(responseDate);
		return invitee;
	}


}
