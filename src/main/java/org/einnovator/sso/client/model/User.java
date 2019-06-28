package org.einnovator.sso.client.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.einnovator.sso.client.modelx.GroupPredicates;
import org.einnovator.util.model.Address;
import org.einnovator.util.model.EntityBase;
import org.einnovator.util.model.Phone;
import org.einnovator.util.model.ToStringCreator;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends EntityBase {
	
	private String username;

	private String email;

	private String email2;

	private String email3;

	private String firstName;

	private String lastName;
	
	private String title;
	
	private GenderType gender;
	
	private Date birthdate;

	private Phone mobilePhone;
		
	private Phone phone;

	private Phone phone2;

	private String middleName;

	private String nickname;

	private String timezone;

	private String locale;

	private String avatar;
	
	private String idhash;

	private String idicon;

	private String cover;

	private String cover2;

	private String signature;

	private String website;

	private String social;

	private String social2;
	
	private UserStatus status;
	
	private UserType type;
	
	private Boolean enabled;

	private String currentGroup;

	private String profileGroup;

	private Boolean authenticated;

	private Integer onboarding;

	private Address address;
	
	private String description;

	private byte[] password;

	private String securityQuestion;
	
	private String securityAnswer;
	
	private Map<String, Object> profile = new LinkedHashMap<>();
	
	private List<Member> membership;

	private List<Role> roles;
	
	@JsonCreator
	public User() {
	}
	
	public User(String username) {
		this.username = username;
	}
	
	public User(Object obj) {
		super(obj);
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
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get the value of property {@code email}.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the value of property {@code email}.
	 *
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get the value of property {@code email2}.
	 *
	 * @return the email2
	 */
	public String getEmail2() {
		return email2;
	}

	/**
	 * Set the value of property {@code email2}.
	 *
	 * @param email2 the email2 to set
	 */
	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	/**
	 * Get the value of property {@code email3}.
	 *
	 * @return the email3
	 */
	public String getEmail3() {
		return email3;
	}

	/**
	 * Set the value of property {@code email3}.
	 *
	 * @param email3 the email3 to set
	 */
	public void setEmail3(String email3) {
		this.email3 = email3;
	}

	/**
	 * Get the value of property {@code firstName}.
	 *
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Set the value of property {@code firstName}.
	 *
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get the value of property {@code lastName}.
	 *
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set the value of property {@code lastName}.
	 *
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Get the value of property {@code title}.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the value of property {@code title}.
	 *
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the value of property {@code gender}.
	 *
	 * @return the gender
	 */
	public GenderType getGender() {
		return gender;
	}

	/**
	 * Set the value of property {@code gender}.
	 *
	 * @param gender the gender to set
	 */
	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	/**
	 * Get the value of property {@code birthdate}.
	 *
	 * @return the birthdate
	 */
	public Date getBirthdate() {
		return birthdate;
	}

	/**
	 * Set the value of property {@code birthdate}.
	 *
	 * @param birthdate the birthdate to set
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * Get the value of property {@code mobilePhone}.
	 *
	 * @return the mobilePhone
	 */
	public Phone getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * Set the value of property {@code mobilePhone}.
	 *
	 * @param mobilePhone the mobilePhone to set
	 */
	public void setMobilePhone(Phone mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
	 * Get the value of property {@code phone}.
	 *
	 * @return the phone
	 */
	public Phone getPhone() {
		return phone;
	}

	/**
	 * Set the value of property {@code phone}.
	 *
	 * @param phone the phone to set
	 */
	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	/**
	 * Get the value of property {@code phone2}.
	 *
	 * @return the phone2
	 */
	public Phone getPhone2() {
		return phone2;
	}

	/**
	 * Set the value of property {@code phone2}.
	 *
	 * @param phone2 the phone2 to set
	 */
	public void setPhone2(Phone phone2) {
		this.phone2 = phone2;
	}

	/**
	 * Get the value of property {@code middleName}.
	 *
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Set the value of property {@code middleName}.
	 *
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * Get the value of property {@code nickname}.
	 *
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Set the value of property {@code nickname}.
	 *
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Get the value of property {@code timezone}.
	 *
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * Set the value of property {@code timezone}.
	 *
	 * @param timezone the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/**
	 * Get the value of property {@code locale}.
	 *
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * Set the value of property {@code locale}.
	 *
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * Get the value of property {@code avatar}.
	 *
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * Set the value of property {@code avatar}.
	 *
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * Get the value of property {@code idhash}.
	 *
	 * @return the idhash
	 */
	public String getIdhash() {
		return idhash;
	}

	/**
	 * Set the value of property {@code idhash}.
	 *
	 * @param idhash the idhash to set
	 */
	public void setIdhash(String idhash) {
		this.idhash = idhash;
	}

	/**
	 * Get the value of property {@code idicon}.
	 *
	 * @return the idicon
	 */
	public String getIdicon() {
		return idicon;
	}

	/**
	 * Set the value of property {@code idicon}.
	 *
	 * @param idicon the idicon to set
	 */
	public void setIdicon(String idicon) {
		this.idicon = idicon;
	}

	/**
	 * Get the value of property {@code cover}.
	 *
	 * @return the cover
	 */
	public String getCover() {
		return cover;
	}

	/**
	 * Set the value of property {@code cover}.
	 *
	 * @param cover the cover to set
	 */
	public void setCover(String cover) {
		this.cover = cover;
	}

	/**
	 * Get the value of property {@code cover2}.
	 *
	 * @return the cover2
	 */
	public String getCover2() {
		return cover2;
	}

	/**
	 * Set the value of property {@code cover2}.
	 *
	 * @param cover2 the cover2 to set
	 */
	public void setCover2(String cover2) {
		this.cover2 = cover2;
	}

	/**
	 * Get the value of property {@code signature}.
	 *
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * Set the value of property {@code signature}.
	 *
	 * @param signature the signature to set
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * Get the value of property {@code website}.
	 *
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * Set the value of property {@code website}.
	 *
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * Get the value of property {@code status}.
	 *
	 * @return the status
	 */
	public UserStatus getStatus() {
		return status;
	}

	/**
	 * Set the value of property {@code status}.
	 *
	 * @param status the status to set
	 */
	public void setStatus(UserStatus status) {
		this.status = status;
	}

	/**
	 * Get the value of property {@code type}.
	 *
	 * @return the type
	 */
	public UserType getType() {
		return type;
	}

	/**
	 * Set the value of property {@code type}.
	 *
	 * @param type the type to set
	 */
	public void setType(UserType type) {
		this.type = type;
	}

	/**
	 * Get the value of property {@code enabled}.
	 *
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * Set the value of property {@code enabled}.
	 *
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Get the value of property {@code address}.
	 *
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Set the value of property {@code address}.
	 *
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Get the value of property {@code password}.
	 *
	 * @return the password
	 */
	public byte[] getPassword() {
		return password;
	}

	/**
	 * Set the value of property {@code password}.
	 *
	 * @param password the password to set
	 */
	public void setPassword(byte[] password) {
		this.password = password;
	}

	/**
	 * Get the value of property {@code securityQuestion}.
	 *
	 * @return the securityQuestion
	 */
	public String getSecurityQuestion() {
		return securityQuestion;
	}

	/**
	 * Set the value of property {@code securityQuestion}.
	 *
	 * @param securityQuestion the securityQuestion to set
	 */
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	/**
	 * Get the value of property {@code securityAnswer}.
	 *
	 * @return the securityAnswer
	 */
	public String getSecurityAnswer() {
		return securityAnswer;
	}

	/**
	 * Set the value of property {@code securityAnswer}.
	 *
	 * @param securityAnswer the securityAnswer to set
	 */
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public Map<String, Object> getProfile() {
		return profile;
	}

	public void setProfile(Map<String, Object> profile) {
		this.profile = profile;
	}

	public Object getProfileAttribute(String name) {
		if (profile!=null) {
			return profile.get(name);
		}
		return null;
	}


	public String getDisplayName() {
		if (StringUtils.hasText(nickname)) {
			return nickname;	
		}
		if (StringUtils.hasText(firstName) && StringUtils.hasText(lastName)) {
			return firstName + " " + lastName;			
		}
		if (StringUtils.hasText(firstName)) {
			return firstName;			
		}
		if (StringUtils.hasText(lastName)) {
			return lastName;			
		}
		return username;
	}
	
	public String getCurrentGroup() {
		return currentGroup;
	}

	public void setCurrentGroup(String currentGroup) {
		this.currentGroup = currentGroup;
	}

	public String getProfileGroup() {
		return profileGroup;
	}

	public void setProfileGroup(String profileGroup) {
		this.profileGroup = profileGroup;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
	}

	public String getSocial() {
		return social;
	}

	public void setSocial(String social) {
		this.social = social;
	}

	public String getSocial2() {
		return social2;
	}

	public void setSocial2(String social2) {
		this.social2 = social2;
	}

	public Integer getOnboarding() {
		return onboarding;
	}

	public void setOnboarding(Integer onboarding) {
		this.onboarding = onboarding;
	}

	public List<Member> getMembership() {
		return membership;
	}

	public void setMembership(List<Member> membership) {
		this.membership = membership;
	}
	
	@JsonIgnore
	public List<Group> getGroups() {
		return Member.toGroups(getMembership());
	}

	@JsonIgnore
	public List<Group> getGroups(GroupType groupType) {
		List<Group> groups = getGroups();
		if (groups==null) {
			return null;
		}
		switch (groupType) {
		case ORGANIZATION: 
			return Group.filter(groups, true, true, GroupPredicates.ORGANIZATION);
		case OPERATION: 
			return Group.filter(groups, true, true, GroupPredicates.OPERATION);
		case DEPARTMENT: 
			return Group.filter(groups, true, true, GroupPredicates.DEPARTMENT);
		case TEAM: 
			return Group.filter(groups, true, true, GroupPredicates.TEAM);
		default:
			return groups;
		}
	}

	public List<String> getGroupsUuid(GroupType groupType) {
		return EntityBase.getUuids(getGroups(groupType));
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@JsonIgnore
	public Group getOrganization() {
		Member member = getProfileMembership();
		return member!=null ? member.getGroup() : null;
	}
	
	@JsonIgnore
	public Member getProfileMembership() {
		if (membership==null || membership.isEmpty()) {
			return null;
		}
		if (profileGroup!=null) {
			Member member = getMembership(profileGroup);
			if (member!=null) {
				return member;
			}
		}
		if (currentGroup!=null) {
			Member member = getMembership(currentGroup);
			if (member!=null) {
				return member;
			}
		}
		for (Member member: membership) {
			if (member.getId()==null && member.getUuid()==null) {
				continue; //sanity check
			}
			return member;
		}
		return null;
	}
	
	@JsonIgnore
	public Member getSelectedMembership(GroupType groupType) {
		if (membership==null || membership.isEmpty()) {
			return null;
		}
		if (currentGroup!=null) {
			Member member = getMembership(currentGroup);
			if (member!=null) {
				return member;
			}
		}
		if (profileGroup!=null) {
			Member member = getMembership(profileGroup);
			if (member!=null) {
				return member;
			}
		}
		for (Member member: membership) {
			if (member.getId()==null && member.getUuid()==null) {
				continue; //sanity check
			}
			return member;
		}
		return null;
	}

	@JsonIgnore
	public Group getSelectedGroup(GroupType groupType) {
		Member member = getSelectedMembership(groupType);
		return member!=null ? member.getGroup() : null;
	}

	@JsonIgnore
	public Member getMembership(String groupId) {
		if (membership==null || membership.isEmpty()) {
			return null;
		}
		for (Member member: membership) {
			if (member.getGroup()!=null && groupId.equals(member.getGroup().getUuid())) {
				return member;
			}
		}
		return null;
	}

	@JsonIgnore
	public Group getGroup(String groupId) {
		Member member = getMembership(groupId);
		return member!=null ? member.getGroup() : null;
	}
	
	@JsonIgnore
	public List<String> getGroupsUuid() {
		List<Group> groups = Group.flatTrees(Group.copy(getGroups(), true), true);
		return EntityBase.getUuids(groups);
	}
	
	@Override
	public ToStringCreator toString1(ToStringCreator builder) {
		return builder
				.append("username", username)
				.append("email", email)
				.append("email2", email2)
				.append("email3", email3)
				.append("firstName", firstName)
				.append("lastName", lastName)
				.append("middleName", middleName)
				.append("nickname", nickname)
				.append("title", title)
				.append("mobilePhone", mobilePhone)
				.append("phone", phone)
				.append("phone2", phone2)
				.append("gender", gender)
				.append("birthdate", birthdate)
				.append("type", type)
				.append("status", status)
				.append("enabled", enabled)
				.append("onboarding", onboarding)
				.append("authenticated", authenticated)
				.append("avatar", avatar)
				.append("idhash", idhash)
				.append("idicon", idicon)
				.append("cover", cover)
				.append("cover2", cover2)
				.append("signature", signature)
				.append("profile", profile)
				.append("locale", locale)
				.append("timezone", timezone)
				.append("address", address)
				.append("website", website)
				.append("social", social)
				.append("social2", social2)
				.append("securityQuestion", securityQuestion)
				.append("securityAnswer", securityAnswer)
				.append("currentGroup", currentGroup)
				.append("profileGroup", profileGroup)
				.append("membership", membership)
				.append("roles", roles);
	} 
		
}
