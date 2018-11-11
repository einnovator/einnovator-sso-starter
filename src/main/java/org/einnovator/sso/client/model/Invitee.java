package org.einnovator.sso.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.einnovator.util.model.EntityBase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Invitee extends EntityBase {
	
	private InvitationStatus status;

	private String username;

	private Group group;
	
	private Date sendDate;

	private String action;

	private Date responseDate;

	public Invitee() {
	}
	
	public InvitationStatus getStatus() {
		return status;
	}

	public void setStatus(InvitationStatus status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " ["
				+ (username != null ? "username=" + username + ", " : "")
				+ (action != null ? "action=" + action + ", " : "")
				+ (sendDate != null ? "sendDate=" + sendDate : "") 
				+ (responseDate != null ? "responseDate=" + responseDate : "") 
				+ (group != null ? "group=" + group.getId() + " " + group.getName()  + " " + group.getType() : "") 
				+ "]";
	}
	

	public static List<Group> getGroups(List<Invitee> invitees) {
		if (invitees==null) {
			return null;
		}
		List<Group> groups = new ArrayList<Group>();
		for (Invitee invitee: invitees) {
			if (invitee.getGroup()!=null) {
				groups.add(invitee.getGroup());
			}
		}
		return groups;
	}
}
