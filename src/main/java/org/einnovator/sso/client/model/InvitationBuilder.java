package org.einnovator.sso.client.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class InvitationBuilder  {	

	private String uuid;
	
	private InvitationType type;

	private String otherType;

	private InvitationError error;

	private String owner;

	private String invitee;
	
	private InvitationStatus status;
	
	private String redirectUri;
	
	private String site;

	private String description;

	private String template;

	private String subject;

	private Boolean autoLogin;

	private List<Invitee> invitees;

	private List<Group> groups;	

	private Map<String, Object> env;

	private Map<String, Object> meta;

	public InvitationBuilder() {
	}

	public InvitationBuilder uuid(String uuid) {
		this.uuid = uuid;
		return this;
	}

	public InvitationBuilder invitee(String invitee) {
		this.invitee = invitee;
		return this;
	}

	public InvitationBuilder type(InvitationType type) {
		this.type = type;
		return this;
	}

	public InvitationBuilder type(String otherType) {
		this.otherType = otherType;
		return this;
	}

	public InvitationBuilder error(InvitationError error) {
		this.error = error;
		return this;
	}

	public InvitationBuilder owner(String owner) {
		this.owner = owner;
		return this;
	}

	public InvitationBuilder status(InvitationStatus status) {
		this.status = status;
		return this;
	}

	public InvitationBuilder redirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
		return this;
	}

	public InvitationBuilder site(String site) {
		this.site = site;
		return this;
	}

	public InvitationBuilder description(String description) {
		this.description = description;
		return this;
	}

	public InvitationBuilder template(String template) {
		this.template = template;
		return this;
	}

	public InvitationBuilder subject(String subject) {
		this.subject = subject;
		return this;
	}

	
	public InvitationBuilder env(Map<String, Object> env) {
		this.env = env;
		return this;
	}

	public InvitationBuilder meta(Map<String, Object> meta) {
		this.meta = meta;
		return this;
	}

	public InvitationBuilder autoLogin(Boolean autoLogin) {
		this.autoLogin = autoLogin;
		return this;
	}

	public InvitationBuilder invitees(List<Invitee> invitees) {
		this.invitees = invitees;
		return this;
	}

	public InvitationBuilder invitees(Invitee... invitees) {
		if (invitees!=null) {
			if (this.invitees==null) {
				this.invitees = new ArrayList<>();
			}
			for (Invitee invitee: invitees) {
				if (invitee!=null) {
					this.invitees.add(invitee);					
				}
			}
		}
		return this;
	}

	public InvitationBuilder groups(List<Group> groups) {
		this.groups = groups;
		return this;
	}

	public InvitationBuilder groups(Group... groups) {
		if (groups!=null) {
			if (this.groups==null) {
				this.groups = new ArrayList<>();
			}
			for (Group group: groups) {
				if (group!=null) {
					this.groups.add(group);					
				}
			}
		}
		return this;
	}

	public Invitation build() {
		Invitation invitation = new Invitation();
		invitation.setUuid(uuid);
		invitation.setType(type);
		invitation.setOtherType(otherType);
		invitation.setOwner(owner);
		invitation.setInvitee(invitee);
		invitation.setStatus(status);
		invitation.setError(error);
		invitation.setAutoLogin(autoLogin);		
		invitation.setRedirectUri(redirectUri);
		invitation.setTemplate(template);
		invitation.setSubject(subject);
		invitation.setDescription(description);
		invitation.setSite(site);
		invitation.setGroups(groups);
		invitation.setInvitees(invitees);
		invitation.setEnv(env);
		invitation.setMeta(meta);
		return invitation;
	}
}
