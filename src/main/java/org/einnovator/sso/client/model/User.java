package org.einnovator.sso.client.model;

import java.util.ArrayList;
import java.util.Date;
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
	
	private Map<String, Object> profile;
	
	private List<Member> membership;

	private List<Role> roles;
	
	/**
	 * Create instance of {@code User}.
	 *
	 */
	@JsonCreator
	public User() {
	}
	
	/**
	 * Create instance of {@code User}.
	 *
	 * @param username the username
	 */
	public User(String username) {
		this.username = username;
	}
	
	/**
	 * Create instance of {@code User}.
	 *
	 * @param prototype a prototype
	 */
	public User(Object prototype) {
		super(prototype);
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
	 * @param email the value of property email
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
	 * @param email2 the value of property email2
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
	 * @param email3 the value of property email3
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
	 * @param firstName the value of property firstName
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
	 * @param lastName the value of property lastName
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
	 * @param title the value of property title
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
	 * @param gender the value of property gender
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
	 * @param birthdate the value of property birthdate
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
	 * @param mobilePhone the value of property mobilePhone
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
	 * @param phone the value of property phone
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
	 * @param phone2 the value of property phone2
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
	 * @param middleName the value of property middleName
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
	 * @param nickname the value of property nickname
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
	 * @param timezone the value of property timezone
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
	 * @param locale the value of property locale
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
	 * @param avatar the value of property avatar
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
	 * @param idhash the value of property idhash
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
	 * @param idicon the value of property idicon
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
	 * @param cover the value of property cover
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
	 * @param cover2 the value of property cover2
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
	 * @param signature the value of property signature
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
	 * @param website the value of property website
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * Get the value of property {@code social}.
	 *
	 * @return the social
	 */
	public String getSocial() {
		return social;
	}

	/**
	 * Set the value of property {@code social}.
	 *
	 * @param social the value of property social
	 */
	public void setSocial(String social) {
		this.social = social;
	}

	/**
	 * Get the value of property {@code social2}.
	 *
	 * @return the social2
	 */
	public String getSocial2() {
		return social2;
	}

	/**
	 * Set the value of property {@code social2}.
	 *
	 * @param social2 the value of property social2
	 */
	public void setSocial2(String social2) {
		this.social2 = social2;
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
	 * @param status the value of property status
	 */
	public void setStatus(UserStatus status) {
		this.status = status;
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
	 * @param enabled the value of property enabled
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Get the value of property {@code currentGroup}.
	 *
	 * @return the currentGroup
	 */
	public String getCurrentGroup() {
		return currentGroup;
	}

	/**
	 * Set the value of property {@code currentGroup}.
	 *
	 * @param currentGroup the value of property currentGroup
	 */
	public void setCurrentGroup(String currentGroup) {
		this.currentGroup = currentGroup;
	}

	/**
	 * Get the value of property {@code profileGroup}.
	 *
	 * @return the profileGroup
	 */
	public String getProfileGroup() {
		return profileGroup;
	}

	/**
	 * Set the value of property {@code profileGroup}.
	 *
	 * @param profileGroup the value of property profileGroup
	 */
	public void setProfileGroup(String profileGroup) {
		this.profileGroup = profileGroup;
	}

	/**
	 * Get the value of property {@code authenticated}.
	 *
	 * @return the authenticated
	 */
	public Boolean getAuthenticated() {
		return authenticated;
	}

	/**
	 * Set the value of property {@code authenticated}.
	 *
	 * @param authenticated the value of property authenticated
	 */
	public void setAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
	}

	/**
	 * Get the value of property {@code onboarding}.
	 *
	 * @return the onboarding
	 */
	public Integer getOnboarding() {
		return onboarding;
	}

	/**
	 * Set the value of property {@code onboarding}.
	 *
	 * @param onboarding the value of property onboarding
	 */
	public void setOnboarding(Integer onboarding) {
		this.onboarding = onboarding;
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
	 * @param address the value of property address
	 */
	public void setAddress(Address address) {
		this.address = address;
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
	 * @param password the value of property password
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
	 * @param securityQuestion the value of property securityQuestion
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
	 * @param securityAnswer the value of property securityAnswer
	 */
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	/**
	 * Get the value of property {@code profile}.
	 *
	 * @return the profile
	 */
	public Map<String, Object> getProfile() {
		return profile;
	}

	/**
	 * Set the value of property {@code profile}.
	 *
	 * @param profile the value of property profile
	 */
	public void setProfile(Map<String, Object> profile) {
		this.profile = profile;
	}

	/**
	 * Get the value of property {@code membership}.
	 *
	 * @return the membership
	 */
	public List<Member> getMembership() {
		return membership;
	}

	/**
	 * Set the value of property {@code membership}.
	 *
	 * @param membership the value of property membership
	 */
	public void setMembership(List<Member> membership) {
		this.membership = membership;
	}

	/**
	 * Get the value of property {@code roles}.
	 *
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * Set the value of property {@code roles}.
	 *
	 * @param roles the value of property roles
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	//
	// With
	//
	
	/**
	 * Set the value of property {@code username}.
	 *
	 * @param username the value of property username
	 * @return this {@code User}
	 */
	public User withUsername(String username) {
		this.username = username;
		return this;
	}

	/**
	 * Set the value of property {@code email}.
	 *
	 * @param email the value of property email
	 * @return this {@code User}
	 */
	public User withEmail(String email) {
		this.email = email;
		return this;
	}

	/**
	 * Set the value of property {@code email2}.
	 *
	 * @param email2 the value of property email2
	 * @return this {@code User}
	 */
	public User withEmail2(String email2) {
		this.email2 = email2;
		return this;
	}

	/**
	 * Set the value of property {@code email3}.
	 *
	 * @param email3 the value of property email3
	 * @return this {@code User}
	 */
	public User withEmail3(String email3) {
		this.email3 = email3;
		return this;
	}

	/**
	 * Set the value of property {@code firstName}.
	 *
	 * @param firstName the value of property firstName
	 * @return this {@code User}
	 */
	public User withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	/**
	 * Set the value of property {@code lastName}.
	 *
	 * @param lastName the value of property lastName
	 * @return this {@code User}
	 */
	public User withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	/**
	 * Set the value of property {@code title}.
	 *
	 * @param title the value of property title
	 * @return this {@code User}
	 */
	public User withTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * Set the value of property {@code gender}.
	 *
	 * @param gender the value of property gender
	 * @return this {@code User}
	 */
	public User withGender(GenderType gender) {
		this.gender = gender;
		return this;
	}

	/**
	 * Set the value of property {@code birthdate}.
	 *
	 * @param birthdate the value of property birthdate
	 * @return this {@code User}
	 */
	public User withBirthdate(Date birthdate) {
		this.birthdate = birthdate;
		return this;
	}

	/**
	 * Set the value of property {@code mobilePhone}.
	 *
	 * @param mobilePhone the value of property mobilePhone
	 * @return this {@code User}
	 */
	public User withMobilePhone(Phone mobilePhone) {
		this.mobilePhone = mobilePhone;
		return this;
	}

	/**
	 * Set the value of property {@code phone}.
	 *
	 * @param phone the value of property phone
	 * @return this {@code User}
	 */
	public User withPhone(Phone phone) {
		this.phone = phone;
		return this;
	}

	/**
	 * Set the value of property {@code phone2}.
	 *
	 * @param phone2 the value of property phone2
	 * @return this {@code User}
	 */
	public User withPhone2(Phone phone2) {
		this.phone2 = phone2;
		return this;
	}

	/**
	 * Set the value of property {@code middleName}.
	 *
	 * @param middleName the value of property middleName
	 * @return this {@code User}
	 */
	public User withMiddleName(String middleName) {
		this.middleName = middleName;
		return this;
	}

	/**
	 * Set the value of property {@code nickname}.
	 *
	 * @param nickname the value of property nickname
	 * @return this {@code User}
	 */
	public User withNickname(String nickname) {
		this.nickname = nickname;
		return this;
	}

	/**
	 * Set the value of property {@code timezone}.
	 *
	 * @param timezone the value of property timezone
	 * @return this {@code User}
	 */
	public User withTimezone(String timezone) {
		this.timezone = timezone;
		return this;
	}

	/**
	 * Set the value of property {@code locale}.
	 *
	 * @param locale the value of property locale
	 * @return this {@code User}
	 */
	public User withLocale(String locale) {
		this.locale = locale;
		return this;
	}

	/**
	 * Set the value of property {@code avatar}.
	 *
	 * @param avatar the value of property avatar
	 * @return this {@code User}
	 */
	public User withAvatar(String avatar) {
		this.avatar = avatar;
		return this;
	}

	/**
	 * Set the value of property {@code idhash}.
	 *
	 * @param idhash the value of property idhash
	 * @return this {@code User}
	 */
	public User withIdhash(String idhash) {
		this.idhash = idhash;
		return this;
	}

	/**
	 * Set the value of property {@code idicon}.
	 *
	 * @param idicon the value of property idicon
	 * @return this {@code User}
	 */
	public User withIdicon(String idicon) {
		this.idicon = idicon;
		return this;
	}

	/**
	 * Set the value of property {@code cover}.
	 *
	 * @param cover the value of property cover
	 * @return this {@code User}
	 */
	public User withCover(String cover) {
		this.cover = cover;
		return this;
	}

	/**
	 * Set the value of property {@code cover2}.
	 *
	 * @param cover2 the value of property cover2
	 * @return this {@code User}
	 */
	public User withCover2(String cover2) {
		this.cover2 = cover2;
		return this;
	}

	/**
	 * Set the value of property {@code signature}.
	 *
	 * @param signature the value of property signature
	 * @return this {@code User}
	 */
	public User withSignature(String signature) {
		this.signature = signature;
		return this;
	}

	/**
	 * Set the value of property {@code website}.
	 *
	 * @param website the value of property website
	 * @return this {@code User}
	 */
	public User withWebsite(String website) {
		this.website = website;
		return this;
	}

	/**
	 * Set the value of property {@code social}.
	 *
	 * @param social the value of property social
	 * @return this {@code User}
	 */
	public User withSocial(String social) {
		this.social = social;
		return this;
	}

	/**
	 * Set the value of property {@code social2}.
	 *
	 * @param social2 the value of property social2
	 * @return this {@code User}
	 */
	public User withSocial2(String social2) {
		this.social2 = social2;
		return this;
	}

	/**
	 * Set the value of property {@code status}.
	 *
	 * @param status the value of property status
	 * @return this {@code User}
	 */
	public User withStatus(UserStatus status) {
		this.status = status;
		return this;
	}

	/**
	 * Set the value of property {@code enabled}.
	 *
	 * @param enabled the value of property enabled
	 * @return this {@code User}
	 */
	public User withEnabled(Boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	/**
	 * Set the value of property {@code currentGroup}.
	 *
	 * @param currentGroup the value of property currentGroup
	 * @return this {@code User}
	 */
	public User withCurrentGroup(String currentGroup) {
		this.currentGroup = currentGroup;
		return this;
	}

	/**
	 * Set the value of property {@code profileGroup}.
	 *
	 * @param profileGroup the value of property profileGroup
	 * @return this {@code User}
	 */
	public User withProfileGroup(String profileGroup) {
		this.profileGroup = profileGroup;
		return this;
	}

	/**
	 * Set the value of property {@code authenticated}.
	 *
	 * @param authenticated the value of property authenticated
	 * @return this {@code User}
	 */
	public User withAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
		return this;
	}

	/**
	 * Set the value of property {@code onboarding}.
	 *
	 * @param onboarding the value of property onboarding
	 * @return this {@code User}
	 */
	public User withOnboarding(Integer onboarding) {
		this.onboarding = onboarding;
		return this;
	}

	/**
	 * Set the value of property {@code address}.
	 *
	 * @param address the value of property address
	 * @return this {@code User}
	 */
	public User withAddress(Address address) {
		this.address = address;
		return this;
	}

	/**
	 * Set the value of property {@code description}.
	 *
	 * @param description the value of property description
	 * @return this {@code User}
	 */
	public User withDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Set the value of property {@code password}.
	 *
	 * @param password the value of property password
	 * @return this {@code User}
	 */
	public User withPassword(byte[] password) {
		this.password = password;
		return this;
	}

	/**
	 * Set the value of property {@code securityQuestion}.
	 *
	 * @param securityQuestion the value of property securityQuestion
	 * @return this {@code User}
	 */
	public User withSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
		return this;
	}

	/**
	 * Set the value of property {@code securityAnswer}.
	 *
	 * @param securityAnswer the value of property securityAnswer
	 * @return this {@code User}
	 */
	public User withSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
		return this;
	}

	/**
	 * Set the value of property {@code profile}.
	 *
	 * @param profile the value of property profile
	 * @return this {@code User}
	 */
	public User withProfile(Map<String, Object> profile) {
		this.profile = profile;
		return this;
	}

	/**
	 * Set the value of property {@code membership}.
	 *
	 * @param membership the value of property membership
	 * @return this {@code User}
	 */
	public User withMembership(List<Member> membership) {
		this.membership = membership;
		return this;
	}

	/**
	 * Set the value of property {@code roles}.
	 *
	 * @param roles the value of property roles
	 * @return this {@code User}
	 */
	public User withRoles(List<Role> roles) {
		this.roles = roles;
		return this;
	}

	//
	// util
	//
	
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

	@JsonIgnore
	public List<Group> getGroups() {
		return Member.getGroups(membership);
	}

	@JsonIgnore
	public List<Group> getGroups(GroupType groupType) {
		List<Group> groups = getGroups();
		if (groupType==null) {
			return groups;
		}
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

	@JsonIgnore
	public List<Group> getOrganizations() {
		return getGroups(GroupType.ORGANIZATION);
	}

	@JsonIgnore
	public List<Group> getTeams() {
		return getGroups(GroupType.TEAM);
	}

	public List<String> getGroupsUuid(GroupType groupType) {
		return EntityBase.getUuids(getGroups(groupType));
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

	@JsonIgnore
	public String getTheAvatar() {
		if (StringUtils.hasText(avatar)) {
			return avatar;
		}
		if (StringUtils.hasText(idicon)) {
			return idicon;
		}
		return null;
	}

	@JsonIgnore
	public String getRequiredAvatar(String defaultAvatar) {
		if (StringUtils.hasText(avatar)) {
			return avatar;
		}
		if (StringUtils.hasText(idicon)) {
			return idicon;
		}
		return defaultAvatar;
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
	
	//
	// static util
	//
	
	public static List<String> getUsernames(Iterable<User> users) {
		List<String> usernames = new ArrayList<>();
		if (users!=null) {
			for (User user: users) {
				if (StringUtils.hasText(user.username)) {
					usernames.add(user.username);
				}
			}			
		}
		return usernames;
	}

		
}
