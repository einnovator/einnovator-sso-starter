package org.einnovator.sso.client.model;

import java.util.Date;

public class InvitationFilter {

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


	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" 
				+ (status != null ? "status=" + status + ", " : "")
				+ (type != null ? "type=" + type + ", " : "") 
				+ (incoming != null ? "incoming=" + incoming + ", " : "")
				+ (q != null ? "q=" + q + ", " : "") 
				+ (owner != null ? "owner=" + owner + ", " : "")
				+ (invitee != null ? "invitee=" + invitee + ", " : "")
				+ (senderGroup != null ? "senderGroup=" + senderGroup + ", " : "")
				+ (inviteeGroup != null ? "inviteeGroup=" + inviteeGroup : "") 
				+ (fromDate != null ? "fromDate=" + fromDate : "")
				+ (toDate != null ? "toDate=" + toDate : "")
				+ "]";
	}

	
	
}
