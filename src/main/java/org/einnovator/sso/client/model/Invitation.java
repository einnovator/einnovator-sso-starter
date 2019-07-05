package org.einnovator.sso.client.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.einnovator.util.MappingUtils;
import org.einnovator.util.model.EntityBase;
import org.einnovator.util.model.ToStringCreator;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Invitation  extends EntityBase {	

	private InvitationType type;

	private String otherType;

	private String app;

	private String invitee;

	private User invitedUser;
	
	private String owner;
	
	private User user;

	private InvitationStatus status;

	private InvitationError error;

	private String redirectUri;
	
	private String site;

	private String description;

	private String subject;
	
	private String template;
	
	private Boolean autoLogin;

	private List<Invitee> invitees;

	private List<Group> groups;	

	private String formattedDate;
	
	private Trigger trigger0;

	private Trigger trigger1;

	private Map<String, Object> env;

	private Map<String, Object> meta;

	public Invitation() {
	}

	public Invitation(Invitation invitation) {
		super(invitation);
	}

	
	/**
	 * Get the value of property {@code type}.
	 *
	 * @return the type
	 */
	public InvitationType getType() {
		return type;
	}

	/**
	 * Set the value of property {@code type}.
	 *
	 * @param type the type to set
	 */
	public void setType(InvitationType type) {
		this.type = type;
	}

	/**
	 * Get the value of property {@code otherType}.
	 *
	 * @return the otherType
	 */
	public String getOtherType() {
		return otherType;
	}

	/**
	 * Set the value of property {@code otherType}.
	 *
	 * @param otherType the otherType to set
	 */
	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}

	/**
	 * Get the value of property {@code app}.
	 *
	 * @return the app
	 */
	public String getApp() {
		return app;
	}

	/**
	 * Set the value of property {@code app}.
	 *
	 * @param app the app to set
	 */
	public void setApp(String app) {
		this.app = app;
	}

	/**
	 * Get the value of property {@code invitee}.
	 *
	 * @return the invitee
	 */
	public String getInvitee() {
		return invitee;
	}

	/**
	 * Set the value of property {@code invitee}.
	 *
	 * @param invitee the invitee to set
	 */
	public void setInvitee(String invitee) {
		this.invitee = invitee;
	}

	/**
	 * Get the value of property {@code invitedUser}.
	 *
	 * @return the invitedUser
	 */
	public User getInvitedUser() {
		return invitedUser;
	}

	/**
	 * Set the value of property {@code invitedUser}.
	 *
	 * @param invitedUser the invitedUser to set
	 */
	public void setInvitedUser(User invitedUser) {
		this.invitedUser = invitedUser;
	}

	/**
	 * Get the value of property {@code owner}.
	 *
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Set the value of property {@code owner}.
	 *
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Get the value of property {@code user}.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Set the value of property {@code user}.
	 *
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Get the value of property {@code status}.
	 *
	 * @return the status
	 */
	public InvitationStatus getStatus() {
		return status;
	}

	/**
	 * Set the value of property {@code status}.
	 *
	 * @param status the status to set
	 */
	public void setStatus(InvitationStatus status) {
		this.status = status;
	}

	/**
	 * Get the value of property {@code error}.
	 *
	 * @return the error
	 */
	public InvitationError getError() {
		return error;
	}

	/**
	 * Set the value of property {@code error}.
	 *
	 * @param error the error to set
	 */
	public void setError(InvitationError error) {
		this.error = error;
	}

	/**
	 * Get the value of property {@code redirectUri}.
	 *
	 * @return the redirectUri
	 */
	public String getRedirectUri() {
		return redirectUri;
	}

	/**
	 * Set the value of property {@code redirectUri}.
	 *
	 * @param redirectUri the redirectUri to set
	 */
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	/**
	 * Get the value of property {@code site}.
	 *
	 * @return the site
	 */
	public String getSite() {
		return site;
	}

	/**
	 * Set the value of property {@code site}.
	 *
	 * @param site the site to set
	 */
	public void setSite(String site) {
		this.site = site;
	}

	/**
	 * Get the value of property {@code description}.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the value of property {@code description}.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the value of property {@code subject}.
	 *
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Set the value of property {@code subject}.
	 *
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Get the value of property {@code template}.
	 *
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * Set the value of property {@code template}.
	 *
	 * @param template the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	
	/**
	 * Get the value of property {@code autoLogin}.
	 *
	 * @return the autoLogin
	 */
	public Boolean getAutoLogin() {
		return autoLogin;
	}

	/**
	 * Set the value of property {@code autoLogin}.
	 *
	 * @param autoLogin the autoLogin to set
	 */
	public void setAutoLogin(Boolean autoLogin) {
		this.autoLogin = autoLogin;
	}

	/**
	 * Get the value of property {@code invitees}.
	 *
	 * @return the invitees
	 */
	public List<Invitee> getInvitees() {
		return invitees;
	}

	/**
	 * Set the value of property {@code invitees}.
	 *
	 * @param invitees the invitees to set
	 */
	public void setInvitees(List<Invitee> invitees) {
		this.invitees = invitees;
	}

	/**
	 * Get the value of property {@code groups}.
	 *
	 * @return the groups
	 */
	public List<Group> getGroups() {
		return groups;
	}

	/**
	 * Set the value of property {@code groups}.
	 *
	 * @param groups the groups to set
	 */
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	/**
	 * Get the value of property {@code formattedDate}.
	 *
	 * @return the formattedDate
	 */
	public String getFormattedDate() {
		return formattedDate;
	}

	/**
	 * Set the value of property {@code formattedDate}.
	 *
	 * @param formattedDate the formattedDate to set
	 */
	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}

	/**
	 * Get the value of property {@code env}.
	 *
	 * @return the env
	 */
	public Map<String, Object> getEnv() {
		return env;
	}

	/**
	 * Set the value of property {@code env}.
	 *
	 * @param env the env to set
	 */
	public void setEnv(Map<String, Object> env) {
		this.env = env;
	}

	
	/**
	 * Get the value of property {@code meta}.
	 *
	 * @return the meta
	 */
	public Map<String, Object> getMeta() {
		return meta;
	}

	/**
	 * Set the value of property {@code meta}.
	 *
	 * @param meta the meta to set
	 */
	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}

	/**
	 * Get the value of property {@code trigger0}.
	 *
	 * @return the trigger0
	 */
	public Trigger getTrigger0() {
		return trigger0;
	}

	/**
	 * Set the value of property {@code trigger0}.
	 *
	 * @param trigger0 the trigger0 to set
	 */
	public void setTrigger0(Trigger trigger0) {
		this.trigger0 = trigger0;
	}

	/**
	 * Get the value of property {@code trigger1}.
	 *
	 * @return the trigger1
	 */
	public Trigger getTrigger1() {
		return trigger1;
	}

	/**
	 * Set the value of property {@code trigger1}.
	 *
	 * @param trigger1 the trigger1 to set
	 */
	public void setTrigger1(Trigger trigger1) {
		this.trigger1 = trigger1;
	}

	public static Invitation makeInvitation(Map<String, Object> map) {
		Invitation invitation = MappingUtils.makeObject(Invitation.class, map);
		return invitation;
	}

	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
			.append("type", type)
			.append("otherType", otherType)
			.append("invitee", invitee)
			.append("owner", owner)
			.append("invitees", invitees)
			.append("status", status)
			.append("error", error)
			.append("groups", groups)
			.append("template", template)
			.append("subject", subject)
			.append("description", description)
			.append("redirectUri", redirectUri)
			.append("autoLogin", autoLogin)
			.append("site", site)
			.append("env", env)
			.append("meta", meta)
			;
	}
	
}
