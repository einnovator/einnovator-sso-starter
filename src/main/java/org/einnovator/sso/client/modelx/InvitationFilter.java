package org.einnovator.sso.client.modelx;

import java.util.Date;

import org.einnovator.sso.client.model.InvitationStatus;
import org.einnovator.sso.client.model.InvitationType;
import org.einnovator.util.model.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvitationFilter extends InvitationOptions {

	private InvitationStatus status;
	
	private InvitationType type;
	
	private Boolean incoming;
	
	private String q;
	
	private String owner;

	private String invitee;

	private String senderGroup;

	private String inviteeGroup;
	
	private Date fromDate;
	
	private Date toDate;
	

	public InvitationStatus getStatus() {
		return status;
	}

	public void setStatus(InvitationStatus status) {
		this.status = status;
	}

	public InvitationType getType() {
		return type;
	}

	public void setType(InvitationType type) {
		this.type = type;
	}

	public Boolean getIncoming() {
		return incoming;
	}

	public void setIncoming(Boolean incoming) {
		this.incoming = incoming;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getInvitee() {
		return invitee;
	}

	public void setInvitee(String invitee) {
		this.invitee = invitee;
	}

	public String getSenderGroup() {
		return senderGroup;
	}

	public void setSenderGroup(String senderGroup) {
		this.senderGroup = senderGroup;
	}

	public String getInviteeGroup() {
		return inviteeGroup;
	}

	public void setInviteeGroup(String inviteeGroup) {
		this.inviteeGroup = inviteeGroup;
	}
	
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}


	/* (non-Javadoc)
	 * @see org.einnovator.util.model.ObjectBase#toString1(org.einnovator.util.model.ToStringCreator)
	 */
	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
				.append("status", status)
				.append("type", type)
				.append("incoming", incoming)
				.append("q", q)
				.append("owner", owner)
				.append("invitee", invitee)
				.append("senderGroup", senderGroup)
				.append("inviteeGroup", inviteeGroup)
				.append("fromDate", fromDate)
				.append("toDate", toDate)
				;
	}
	
}
