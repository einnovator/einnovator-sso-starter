package org.einnovator.sso.client.modelx;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.einnovator.sso.client.model.GenderType;
import org.einnovator.util.model.Address;
import org.einnovator.util.model.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A filter for {@code Users}s.
 * 
 * @see org.einnovator.sso.client.manager.UserManager
 * @see org.einnovator.sso.client.model.User
 * @author support@einnovator.org
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFilter extends UserOptions {

	private String q;
	
	private String groupId;
	
	private List<String> groups;

	private List<String> roles;

	private GenderType gender;
	
	private Date startBirthdate;

	private Date endBirthdate;

	private Integer minAge;
	
	private Integer maxAge;
	
	private String[] language;

	private String[] ethnic;

	private String phone;

	private Address address;
	
	private Boolean email;

	private Boolean qaddress;

	private Boolean enabled;

	public UserFilter() {
	}

	//
	// Getters/Setters
	//


	/**
	 * Get the value of property {@code q}.
	 *
	 * @return the q
	 */
	public String getQ() {
		return q;
	}

	/**
	 * Set the value of property {@code q}.
	 *
	 * @param q the value of property q
	 */
	public void setQ(String q) {
		this.q = q;
	}

	/**
	 * Get the value of property {@code groupId}.
	 *
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * Set the value of property {@code groupId}.
	 *
	 * @param groupId the value of property groupId
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * Get the value of property {@code groups}.
	 *
	 * @return the groups
	 */
	public List<String> getGroups() {
		return groups;
	}

	/**
	 * Set the value of property {@code groups}.
	 *
	 * @param groups the value of property groups
	 */
	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	/**
	 * Get the value of property {@code roles}.
	 *
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}

	/**
	 * Set the value of property {@code roles}.
	 *
	 * @param roles the value of property roles
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
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
	 * Get the value of property {@code startBirthdate}.
	 *
	 * @return the startBirthdate
	 */
	public Date getStartBirthdate() {
		return startBirthdate;
	}

	/**
	 * Set the value of property {@code startBirthdate}.
	 *
	 * @param startBirthdate the value of property startBirthdate
	 */
	public void setStartBirthdate(Date startBirthdate) {
		this.startBirthdate = startBirthdate;
	}

	/**
	 * Get the value of property {@code endBirthdate}.
	 *
	 * @return the endBirthdate
	 */
	public Date getEndBirthdate() {
		return endBirthdate;
	}

	/**
	 * Set the value of property {@code endBirthdate}.
	 *
	 * @param endBirthdate the value of property endBirthdate
	 */
	public void setEndBirthdate(Date endBirthdate) {
		this.endBirthdate = endBirthdate;
	}

	/**
	 * Get the value of property {@code minAge}.
	 *
	 * @return the minAge
	 */
	public Integer getMinAge() {
		return minAge;
	}

	/**
	 * Set the value of property {@code minAge}.
	 *
	 * @param minAge the value of property minAge
	 */
	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}

	/**
	 * Get the value of property {@code maxAge}.
	 *
	 * @return the maxAge
	 */
	public Integer getMaxAge() {
		return maxAge;
	}

	/**
	 * Set the value of property {@code maxAge}.
	 *
	 * @param maxAge the value of property maxAge
	 */
	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

	/**
	 * Get the value of property {@code language}.
	 *
	 * @return the language
	 */
	public String[] getLanguage() {
		return language;
	}

	/**
	 * Set the value of property {@code language}.
	 *
	 * @param language the value of property language
	 */
	public void setLanguage(String[] language) {
		this.language = language;
	}

	/**
	 * Get the value of property {@code ethnic}.
	 *
	 * @return the ethnic
	 */
	public String[] getEthnic() {
		return ethnic;
	}

	/**
	 * Set the value of property {@code ethnic}.
	 *
	 * @param ethnic the value of property ethnic
	 */
	public void setEthnic(String[] ethnic) {
		this.ethnic = ethnic;
	}

	/**
	 * Get the value of property {@code phone}.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Set the value of property {@code phone}.
	 *
	 * @param phone the value of property phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	 * Get the value of property {@code email}.
	 *
	 * @return the email
	 */
	public Boolean getEmail() {
		return email;
	}

	/**
	 * Set the value of property {@code email}.
	 *
	 * @param email the value of property email
	 */
	public void setEmail(Boolean email) {
		this.email = email;
	}

	/**
	 * Get the value of property {@code qaddress}.
	 *
	 * @return the qaddress
	 */
	public Boolean getQaddress() {
		return qaddress;
	}

	/**
	 * Set the value of property {@code qaddress}.
	 *
	 * @param qaddress the value of property qaddress
	 */
	public void setQaddress(Boolean qaddress) {
		this.qaddress = qaddress;
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
	
	//
	// Where

	/**
	 * Set the value of property {@code q}.
	 *
	 * @param q the value of property groupId
	 * @return this {@code UserFilter}
	 */
	public UserFilter withQ(String q) {
		this.q = q;
		return this;
	}
	
	/**
	 * Set the value of property {@code groupId}.
	 *
	 * @param groupId the value of property groupId
	 * @return this {@code UserFilter}
	 */
	public UserFilter withGroupId(String groupId) {
		this.groupId = groupId;
		return this;
	}

	/**
	 * Set the value of property {@code groups}.
	 *
	 * @param groups the value of property groups
	 * @return this {@code UserFilter}
	 */
	public UserFilter withGroups(List<String> groups) {
		this.groups = groups;
		return this;
	}

	/**
	 * Set the value of property {@code roles}.
	 *
	 * @param roles the value of property roles
	 * @return this {@code UserFilter}
	 */
	public UserFilter withRoles(List<String> roles) {
		this.roles = roles;
		return this;
	}

	/**
	 * Set the value of property {@code gender}.
	 *
	 * @param gender the value of property gender
	 * @return this {@code UserFilter}
	 */
	public UserFilter withGender(GenderType gender) {
		this.gender = gender;
		return this;
	}

	/**
	 * Set the value of property {@code startBirthdate}.
	 *
	 * @param startBirthdate the value of property startBirthdate
	 * @return this {@code UserFilter}
	 */
	public UserFilter withStartBirthdate(Date startBirthdate) {
		this.startBirthdate = startBirthdate;
		return this;
	}

	/**
	 * Set the value of property {@code endBirthdate}.
	 *
	 * @param endBirthdate the value of property endBirthdate
	 * @return this {@code UserFilter}
	 */
	public UserFilter withEndBirthdate(Date endBirthdate) {
		this.endBirthdate = endBirthdate;
		return this;
	}

	/**
	 * Set the value of property {@code minAge}.
	 *
	 * @param minAge the value of property minAge
	 * @return this {@code UserFilter}
	 */
	public UserFilter withMinAge(Integer minAge) {
		this.minAge = minAge;
		return this;
	}

	/**
	 * Set the value of property {@code maxAge}.
	 *
	 * @param maxAge the value of property maxAge
	 * @return this {@code UserFilter}
	 */
	public UserFilter withMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
		return this;
	}

	/**
	 * Set the value of property {@code language}.
	 *
	 * @param language the value of property language
	 * @return this {@code UserFilter}
	 */
	public UserFilter withLanguage(String[] language) {
		this.language = language;
		return this;
	}

	/**
	 * Set the value of property {@code ethnic}.
	 *
	 * @param ethnic the value of property ethnic
	 * @return this {@code UserFilter}
	 */
	public UserFilter withEthnic(String[] ethnic) {
		this.ethnic = ethnic;
		return this;
	}

	/**
	 * Set the value of property {@code phone}.
	 *
	 * @param phone the value of property phone
	 * @return this {@code UserFilter}
	 */
	public UserFilter withPhone(String phone) {
		this.phone = phone;
		return this;
	}

	/**
	 * Set the value of property {@code address}.
	 *
	 * @param address the value of property address
	 * @return this {@code UserFilter}
	 */
	public UserFilter withAddress(Address address) {
		this.address = address;
		return this;
	}

	/**
	 * Set the value of property {@code email}.
	 *
	 * @param email the value of property email
	 * @return this {@code UserFilter}
	 */
	public UserFilter withEmail(Boolean email) {
		this.email = email;
		return this;
	}

	/**
	 * Set the value of property {@code qaddress}.
	 *
	 * @param qaddress the value of property qaddress
	 * @return this {@code UserFilter}
	 */
	public UserFilter withQaddress(Boolean qaddress) {
		this.qaddress = qaddress;
		return this;
	}

	/**
	 * Set the value of property {@code enabled}.
	 *
	 * @param enabled the value of property enabled
	 * @return this {@code UserFilter}
	 */
	public UserFilter withEnabled(Boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	/* (non-Javadoc)
	 * @see org.einnovator.sso.modelx.UserOptions#toString(org.einnovator.util.model.ToStringCreator)
	 */
	@Override
	public ToStringCreator toString(ToStringCreator creator) {
		return super.toString(creator
				.append("q", q)
				.append("groupId", groupId)
				.append("gender", gender)
				.append("startBirthdate", startBirthdate)
				.append("endBirthdate", endBirthdate)
				.append("minAge", minAge)
				.append("maxAge", maxAge)
				.append("language", language)
				.append("ethnic", ethnic)
				.append("email", email)
				.append("phone", phone)
				.append("enabled", enabled)
				.append("address", address)
				.append("qaddress", qaddress)
				);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
		result = prime * result + ((endBirthdate == null) ? 0 : endBirthdate.hashCode());
		result = prime * result + Arrays.hashCode(ethnic);
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + Arrays.hashCode(language);
		result = prime * result + ((maxAge == null) ? 0 : maxAge.hashCode());
		result = prime * result + ((minAge == null) ? 0 : minAge.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((q == null) ? 0 : q.hashCode());
		result = prime * result + ((qaddress == null) ? 0 : qaddress.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((startBirthdate == null) ? 0 : startBirthdate.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserFilter other = (UserFilter) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enabled == null) {
			if (other.enabled != null)
				return false;
		} else if (!enabled.equals(other.enabled))
			return false;
		if (endBirthdate == null) {
			if (other.endBirthdate != null)
				return false;
		} else if (!endBirthdate.equals(other.endBirthdate))
			return false;
		if (!Arrays.equals(ethnic, other.ethnic))
			return false;
		if (gender != other.gender)
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (!Arrays.equals(language, other.language))
			return false;
		if (maxAge == null) {
			if (other.maxAge != null)
				return false;
		} else if (!maxAge.equals(other.maxAge))
			return false;
		if (minAge == null) {
			if (other.minAge != null)
				return false;
		} else if (!minAge.equals(other.minAge))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (q == null) {
			if (other.q != null)
				return false;
		} else if (!q.equals(other.q))
			return false;
		if (qaddress == null) {
			if (other.qaddress != null)
				return false;
		} else if (!qaddress.equals(other.qaddress))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (startBirthdate == null) {
			if (other.startBirthdate != null)
				return false;
		} else if (!startBirthdate.equals(other.startBirthdate))
			return false;
		return true;
	}
	
	
}
