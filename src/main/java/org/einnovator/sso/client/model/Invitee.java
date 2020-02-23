package org.einnovator.sso.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.einnovator.util.model.EntityBase;
import org.einnovator.util.model.ToStringCreator;

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

	/**
	 * Create instance of {@code Invitee}.
	 *
	 */
	public Invitee() {
	}
	
	/**
	 * @return
	 */
	public InvitationStatus getStatus() {
		return status;
	}

	/**
	 * Get the value of property {@code username}.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the value of property {@code username}.
	 *
	 * @param username the value of property username
	 */
	public void setUsername(String username) {
		this.username = username;
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

	/**
	 * Get the value of property {@code sendDate}.
	 *
	 * @return the sendDate
	 */
	public Date getSendDate() {
		return sendDate;
	}

	/**
	 * Set the value of property {@code sendDate}.
	 *
	 * @param sendDate the value of property sendDate
	 */
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	/**
	 * Get the value of property {@code action}.
	 *
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Set the value of property {@code action}.
	 *
	 * @param action the value of property action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Get the value of property {@code responseDate}.
	 *
	 * @return the responseDate
	 */
	public Date getResponseDate() {
		return responseDate;
	}

	/**
	 * Set the value of property {@code responseDate}.
	 *
	 * @param responseDate the value of property responseDate
	 */
	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	/**
	 * Set the value of property {@code status}.
	 *
	 * @param status the value of property status
	 */
	public void setStatus(InvitationStatus status) {
		this.status = status;
	}

	/**
	 * Set the value of property {@code status}.
	 *
	 * @param status the value of property status
	 */
	public Invitee withStatus(InvitationStatus status) {
		this.status = status;
		return this;
	}

	/**
	 * Set the value of property {@code username}.
	 *
	 * @param username the value of property username
	 */
	public Invitee withUsername(String username) {
		this.username = username;
		return this;
	}

	/**
	 * Set the value of property {@code group}.
	 *
	 * @param group the value of property group
	 */
	public Invitee withGroup(Group group) {
		this.group = group;
		return this;
	}

	/**
	 * Set the value of property {@code sendDate}.
	 *
	 * @param sendDate the value of property sendDate
	 */
	public Invitee withSendDate(Date sendDate) {
		this.sendDate = sendDate;
		return this;
	}

	/**
	 * Set the value of property {@code action}.
	 *
	 * @param action the value of property action
	 */
	public Invitee withAction(String action) {
		this.action = action;
		return this;
	}

	/**
	 * Set the value of property {@code responseDate}.
	 *
	 * @param responseDate the value of property responseDate
	 */
	public Invitee withResponseDate(Date responseDate) {
		this.responseDate = responseDate;
		return this;
	}

	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
			.append("username", username)
			.append("action", action)
			.append("sendDate", sendDate)
			.append("responseDate", responseDate)
			.append("group", group!=null ? group.getUuid() + " " + group.getName()  + " " + group.getType() : "")
			;
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
