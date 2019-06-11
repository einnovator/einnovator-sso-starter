package org.einnovator.sso.client.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.einnovator.util.model.Address;
import org.einnovator.util.model.Phone;


public class UserBuilder {

	private String id;

	private String uuid;

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
	
	private String cover;

	private String cover2;

	private String signature;

	private String website;
	
	private String social;

	private String social2;
	
	private UserStatus status;
	
	private Boolean enabled;

	private Address address;
	
	private String description;
	
	private byte[] password;

	private String securityQuestion;
	
	private String securityAnswer;
	
	private Map<String,Object> profile = new LinkedHashMap<>(); 
	

	
	public UserBuilder id(String id) {
		this.id = id;
		return this;
	}

	public UserBuilder uuid(String uuid) {
		this.uuid = uuid;
		return this;
	}

	public UserBuilder username(String username) {
		this.username = username;
		return this;
	}

	public UserBuilder email(String email) {
		this.email = email;
		return this;
	}

	public UserBuilder email2(String email2) {
		this.email2 = email2;
		return this;
	}

	public UserBuilder email3(String email3) {
		this.email3 = email3;
		return this;
	}

	public UserBuilder title(String title) {
		this.title = title;
		return this;
	}

	public UserBuilder gender(GenderType gender) {
		this.gender = gender;
		return this;
	}

	public UserBuilder birthdate(Date birthdate) {
		this.birthdate = birthdate;
		return this;
	}

	public UserBuilder password(byte[] password) {
		this.password = password;
		return this;
	}

	public UserBuilder firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public UserBuilder lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public UserBuilder middleName(String middleName) {
		this.middleName = middleName;
		return this;
	}

	public UserBuilder nickname(String nickname) {
		this.nickname = nickname;
		return this;
	}

	public UserBuilder mobilePhone(Phone mobilePhone) {
		this.mobilePhone = mobilePhone;
		return this;
	}
	
	public UserBuilder phone(Phone phone) {
		this.phone = phone;
		return this;
	}

	public UserBuilder phone2(Phone phone2) {
		this.phone2 = phone2;
		return this;
	}

	public UserBuilder website(String website) {
		this.website = website;
		return this;
	}

	public UserBuilder social(String social) {
		this.social = social;
		return this;
	}

	public UserBuilder social2(String social2) {
		this.social2 = social2;
		return this;
	}

	public UserBuilder securityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
		return this;
	}

	public UserBuilder securityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
		return this;
	}
	
	public UserBuilder avatar(String avatar) {
		this.avatar = avatar;
		return this;
	}

	public UserBuilder cover(String cover) {
		this.cover = cover;
		return this;
	}

	public UserBuilder signature(String signature) {
		this.signature = signature;
		return this;
	}

	public UserBuilder timezone(String timezone) {
		this.timezone = timezone;
		return this;
	}

	public UserBuilder locale(String locale) {
		this.locale = locale;
		return this;
	}

	public UserBuilder address(Address address) {
		this.address = address;
		return this;
	}

	public UserBuilder profile(Map<String, Object> profile) {
		this.profile = profile;
		return this;
	}
	
	public UserBuilder status(UserStatus status) {
		this.status = status;
		return this;
	}

	public UserBuilder enabled(Boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public UserBuilder description(String description) {
		this.description = description;
		return this;
	}

	
	public User build() {
		User user = new User();
		user.setId(id);
		user.setUuid(uuid);
		user.setUsername(username);
		user.setEmail(email);
		user.setEmail2(email2);
		user.setEmail3(email3);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setMobilePhone(mobilePhone);
		user.setPhone(phone);
		user.setPhone2(phone2);
		user.setTitle(title);
		user.setGender(gender);
		user.setBirthdate(birthdate);
		user.setWebsite(website);
		user.setSocial(social);
		user.setSocial2(social2);
		user.setMiddleName(middleName);
		user.setNickname(nickname);
		user.setAvatar(avatar);
		user.setCover(cover);
		user.setCover2(cover2);
		user.setSignature(signature);
		user.setTimezone(timezone);
		user.setLocale(locale);
		user.setStatus(status);
		user.setEnabled(enabled);
		user.setAddress(address);
		user.setDescription(description);
		user.setProfile(profile);
		user.setPassword(password);
		user.setSecurityQuestion(securityQuestion);
		user.setSecurityAnswer(securityAnswer);
		user.setProfile(profile);
		return user;
	}
	
	
	
}
