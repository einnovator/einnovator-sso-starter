package org.einnovator.sso.client.model;

import java.util.ArrayList;
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

	/**
	 * Create instance of {@code Invitation}.
	 *
	 */
	public Invitation() {
	}

	/**
	 * Create instance of {@code Invitation}.
	 *
	 * @param prototype a prototype
	 */
	public Invitation(Object prototype) {
		super(prototype);
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
	 * @param type the value of property type
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
	 * @param otherType the value of property otherType
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
	 * @param app the value of property app
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
	 * @param invitee the value of property invitee
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
	 * @param invitedUser the value of property invitedUser
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
	 * @param owner the value of property owner
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
	 * @param user the value of property user
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
	 * @param status the value of property status
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
	 * @param error the value of property error
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
	 * @param redirectUri the value of property redirectUri
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
	 * @param site the value of property site
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
	 * @param description the value of property description
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
	 * @param subject the value of property subject
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
	 * @param template the value of property template
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
	 * @param autoLogin the value of property autoLogin
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
	 * @param invitees the value of property invitees
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
	 * @param groups the value of property groups
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
	 * @param formattedDate the value of property formattedDate
	 */
	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
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
	 * @param trigger0 the value of property trigger0
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
	 * @param trigger1 the value of property trigger1
	 */
	public void setTrigger1(Trigger trigger1) {
		this.trigger1 = trigger1;
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
	 * @param env the value of property env
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
	 * @param meta the value of property meta
	 */
	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}
	
	//
	// With
	//

	/**
	 * Set the value of property {@code type}.
	 *
	 * @param type the value of property type
	 * @return this {@code Invitation}
	 */
	public Invitation withType(InvitationType type) {
		this.type = type;
		return this;
	}

	/**
	 * Set the value of property {@code otherType}.
	 *
	 * @param otherType the value of property otherType
	 * @return this {@code Invitation}
	 */
	public Invitation withOtherType(String otherType) {
		this.otherType = otherType;
		return this;
	}

	/**
	 * Set the value of property {@code app}.
	 *
	 * @param app the value of property app
	 * @return this {@code Invitation}
	 */
	public Invitation withApp(String app) {
		this.app = app;
		return this;
	}

	/**
	 * Set the value of property {@code invitee}.
	 *
	 * @param invitee the value of property invitee
	 * @return this {@code Group}
	 * @return this {@code Invitation}
	 */
	public Invitation withInvitee(String invitee) {
		this.invitee = invitee;
		return this;
	}

	/**
	 * Set the value of property {@code invitedUser}.
	 *
	 * @param invitedUser the value of property invitedUser
	 * @return this {@code Invitation}
	 */
	public Invitation withInvitedUser(User invitedUser) {
		this.invitedUser = invitedUser;
		return this;
	}

	/**
	 * Set the value of property {@code owner}.
	 *
	 * @param owner the value of property owner
	 * @return this {@code Invitation}
	 */
	public Invitation withOwner(String owner) {
		this.owner = owner;
		return this;
	}

	/**
	 * Set the value of property {@code user}.
	 *
	 * @param user the value of property user
	 * @return this {@code Invitation}
	 */
	public Invitation withUser(User user) {
		this.user = user;
		return this;
	}

	/**
	 * Set the value of property {@code status}.
	 *
	 * @param status the value of property status
	 * @return this {@code Invitation}
	 */
	public Invitation withStatus(InvitationStatus status) {
		this.status = status;
		return this;
	}

	/**
	 * Set the value of property {@code error}.
	 *
	 * @param error the value of property error
	 * @return this {@code Invitation}
	 */
	public Invitation withError(InvitationError error) {
		this.error = error;
		return this;
	}

	/**
	 * Set the value of property {@code redirectUri}.
	 *
	 * @param redirectUri the value of property redirectUri
	 * @return this {@code Invitation}
	 */
	public Invitation withRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
		return this;
	}

	/**
	 * Set the value of property {@code site}.
	 *
	 * @param site the value of property site
	 * @return this {@code Invitation}
	 */
	public Invitation withSite(String site) {
		this.site = site;
		return this;
	}

	/**
	 * Set the value of property {@code description}.
	 *
	 * @param description the value of property description
	 * @return this {@code Invitation}
	 */
	public Invitation withDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Set the value of property {@code subject}.
	 *
	 * @param subject the value of property subject
	 * @return this {@code Invitation}
	 */
	public Invitation withSubject(String subject) {
		this.subject = subject;
		return this;
	}

	/**
	 * Set the value of property {@code template}.
	 *
	 * @param template the value of property template
	 * @return this {@code Invitation}
	 */
	public Invitation withTemplate(String template) {
		this.template = template;
		return this;
	}

	/**
	 * Set the value of property {@code autoLogin}.
	 *
	 * @param autoLogin the value of property autoLogin
	 * @return this {@code Invitation}
	 */
	public Invitation withAutoLogin(Boolean autoLogin) {
		this.autoLogin = autoLogin;
		return this;
	}

	/**
	 * Set the value of property {@code invitees}.
	 *
	 * @param invitees the value of property invitees
	 * @return this {@code Invitation}
	 */
	public Invitation withInvitees(List<Invitee> invitees) {
		this.invitees = invitees;
		return this;
	}

	/**
	 * Set the value of property {@code formattedDate}.
	 *
	 * @param formattedDate the value of property formattedDate
	 * @return this {@code Invitation}
	 */
	public Invitation withFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
		return this;
	}

	/**
	 * Set the value of property {@code trigger0}.
	 *
	 * @param trigger0 the value of property trigger0
	 * @return this {@code Invitation}
	 */
	public Invitation withTrigger0(Trigger trigger0) {
		this.trigger0 = trigger0;
		return this;
	}

	/**
	 * Set the value of property {@code trigger1}.
	 *
	 * @param trigger1 the value of property trigger1
	 * @return this {@code Invitation}
	 */
	public Invitation withTrigger1(Trigger trigger1) {
		this.trigger1 = trigger1;
		return this;
	}

	/**
	 * Set the value of property {@code env}.
	 *
	 * @param env the value of property env
	 * @return this {@code Invitation}
	 */
	public Invitation withEnv(Map<String, Object> env) {
		this.env = env;
		return this;
	}

	/**
	 * Set the value of property {@code meta}.
	 *
	 * @param meta the value of property meta
	 * @return this {@code Invitation}
	 */
	public Invitation withMeta(Map<String, Object> meta) {
		this.meta = meta;
		return this;
	}

	/**
	 * Set the value of property {@code invitees}.
	 *
	 * @param invitees the value of property invitees
	 * @return this {@code Invitation}
	 */
	public Invitation withInvitees(Invitee... invitees) {
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
	
	/**
	 * Set the value of property {@code groups}.
	 *
	 * @param groups the value of property groups
	 * @return this {@code Invitation}
	 */
	public Invitation withGroups(List<Group> groups) {
		this.groups = groups;
		return this;
	}

	/**
	 * Set the value of property {@code groups}.
	 *
	 * @param groups the value of property groups
	 * @return this {@code Invitation}
	 */
	public Invitation withGroups(Group... groups) {
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
