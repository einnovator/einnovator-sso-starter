package org.einnovator.sso.client.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.einnovator.util.MappingUtils;
import org.einnovator.util.model.EntityBase;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Invitation  extends EntityBase {	

	private String id;

	private String uuid;

	private InvitationType type;

	private String invitee;
	
	private UserType userType;

	private Boolean external;

	private String owner;
	
	private InvitationStatus status;

	private InvitationError error;

	private String redirectUri;
	
	private String site;

	private String description;

	private String img;
	
	private String subject;
	
	private String template;
	
	private Map<String, Object> env;

	private Boolean autoLogin;

	private List<Invitee> invitees;

	private List<Group> groups;	

	private List<Mention> mentions;

	private User user;

	private User invitedUser;
	
	private String formattedDate;
	
	private Trigger trigger0;

	private Trigger trigger1;

	
	public Invitation() {
	}

	public Invitation(Invitation invitation) {
		MappingUtils.updateObjectFromNonNullIgnoreCollections(this, invitation);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getInvitee() {
		return invitee;
	}

	public void setInvitee(String invitee) {
		this.invitee = invitee;
	}

	public InvitationType getType() {
		return type;
	}

	public void setType(InvitationType type) {
		this.type = type;
	}

	public String getTypeDisplayValue() {
		return type!=null ? type.getDisplayValue() : "";
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Boolean getExternal() {
		return external;
	}

	public void setExternal(Boolean external) {
		this.external = external;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}


	public InvitationStatus getStatus() {
		return status;
	}

	public void setStatus(InvitationStatus status) {
		this.status = status;
	}
	
	public InvitationError getError() {
		return error;
	}

	public void setError(InvitationError error) {
		this.error = error;
	}

	public String redirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Map<String, Object> getEnv() {
		return env;
	}

	public void setEnv(Map<String, Object> env) {
		this.env = env;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Boolean getAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(Boolean autoLogin) {
		this.autoLogin = autoLogin;
	}

	public List<Invitee> getInvitees() {
		return invitees;
	}

	public void setInvitees(List<Invitee> invitees) {
		this.invitees = invitees;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Mention> getMentions() {
		return mentions;
	}

	public void setMentions(List<Mention> mentions) {
		this.mentions = mentions;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getInvitedUser() {
		return invitedUser;
	}

	public void setInvitedUser(User invitedUser) {
		this.invitedUser = invitedUser;
	}

	public String getFormattedDate() {
		return formattedDate;
	}

	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}

	public Trigger getTrigger0() {
		return trigger0;
	}

	public void setTrigger0(Trigger trigger0) {
		this.trigger0 = trigger0;
	}

	public Trigger getTrigger1() {
		return trigger1;
	}

	public void setTrigger1(Trigger trigger1) {
		this.trigger1 = trigger1;
	}

	@Override
	public String toString() {
		return Invitation.class.getSimpleName() + " ["
				+ (id != null ? "id=" + id  + ", " : "")
				+ (uuid != null ? "uuid=" + uuid  + ", " : "")
				+ (invitee != null ? "invitee=" + invitee + ", " : "")
				+ (type != null ? "type=" + type + ", " : "")
				+ (userType != null ? "userType=" + userType + ", " : "")
				+ (external != null ? "external=" + external + ", " : "")
				+ (owner != null ? "owner=" + owner + ", " : "")
				+ (status != null ? "status=" + status  + ", " : "") 
				+ (error != null ? "error=" + error  + ", " : "") 
				+ (template != null ? "template=" + template  + ", " : "") 
				+ (subject != null ? "subject=" + subject  + ", " : "") 
				+ (site != null ? "site=" + site  + ", " : "")
				+ (env != null ? "env=" + env  + ", " : "")
				+ (autoLogin != null ? "autoLogin=" + autoLogin  + ", " : "")
				+ (redirectUri != null ? "redirectUri=" + redirectUri + ", ": "")
				+ (img != null ? "status=" + status  + ", " : "") 
				+ (trigger0 != null ? "trigger0=" + trigger0  + ", " : "") 
				+ (trigger1 != null ? "trigger1=" + trigger1  + ", " : "")
				+ (description != null ? "description=" + description : "")
				+ "]";
	}

	

	public static Invitation makeInvitation(Map<String, Object> map) {
		Invitation invitation = MappingUtils.makeObject(Invitation.class, map);
		return invitation;
	}

}
