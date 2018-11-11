package org.einnovator.sso.client.model;

import java.util.List;
import java.util.Map;

public class InvitationBuilder  {	

	private String uuid;
	
	private String invitee;
	
	private InvitationType type;

	private InvitationError error;

	private UserType userType;
	
	private Boolean external;

	private String owner;
	
	private InvitationStatus status;
	
	private String redirectUri;
	
	private String site;

	private String description;

	private String imgUri;
	
	private String template;

	private String subject;

	private Map<String, Object> env;

	private Boolean autoLogin;

	private List<Invitee> invitees;

	private List<Group> groups;	

	private List<Mention> mentions;
	
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

	public InvitationBuilder error(InvitationError error) {
		this.error = error;
		return this;
	}

	public InvitationBuilder userType(UserType userType) {
		this.userType = userType;
		return this;
	}

	public InvitationBuilder external(Boolean external) {
		this.external = external;
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

	public InvitationBuilder imgUri(String imgUri) {
		this.imgUri = imgUri;
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
	
	public InvitationBuilder autoLogin(Boolean autoLogin) {
		this.autoLogin = autoLogin;
		return this;
	}

	public InvitationBuilder invitees(List<Invitee> invitees) {
		this.invitees = invitees;
		return this;
	}

	public InvitationBuilder groups(List<Group> groups) {
		this.groups = groups;
		return this;
	}

	public InvitationBuilder mentions(List<Mention> mentions) {
		this.mentions = mentions;
		return this;
	}

	public Invitation build() {
		Invitation invitation = new Invitation();
		invitation.setUuid(uuid);
		invitation.setType(type);
		invitation.setOwner(owner);
		invitation.setInvitee(invitee);
		invitation.setUserType(userType);
		invitation.setExternal(external);
		invitation.setStatus(status);
		invitation.setError(error);
		invitation.setAutoLogin(autoLogin);		
		invitation.setRedirectUri(redirectUri);
		invitation.setSite(site);
		invitation.setDescription(description);
		invitation.setImgUri(imgUri);
		invitation.setTemplate(template);
		invitation.setSubject(subject);
		invitation.setEnv(env);
		invitation.setGroups(groups);
		invitation.setMentions(mentions);
		invitation.setInvitees(invitees);
		return invitation;
	}
}
