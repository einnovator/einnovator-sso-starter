package org.einnovator.sso.client.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.einnovator.sso.client.modelx.GroupPredicates;
import org.einnovator.util.model.Address;
import org.einnovator.util.model.EntityBase;
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
	
	private String gender;
	
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
		this.id = username;
		this.username = username;
	}
	
	public User(Object obj) {
		super(obj);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getEmail3() {
		return email3;
	}

	public void setEmail3(String email3) {
		this.email3 = email3;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Phone getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(Phone mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public Phone getPhone2() {
		return phone2;
	}

	public void setPhone2(Phone phone2) {
		this.phone2 = phone2;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}


	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getCover2() {
		return cover2;
	}

	public void setCover2(String cover2) {
		this.cover2 = cover2;
	}


	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}


	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}
	
	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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
