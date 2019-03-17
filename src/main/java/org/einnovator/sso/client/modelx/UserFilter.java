package org.einnovator.sso.client.modelx;

import java.util.Date;
import java.util.List;

import org.einnovator.sso.client.model.Address;
import org.einnovator.sso.client.model.GenderType;
import org.einnovator.util.model.ToStringCreator;

public class UserFilter extends UserOptions {

	private String q;
	
	private String groupId;
	
	private List<String> groups;

	private List<String> roles;

	private List<String> permisssions;
	
	private GenderType gender;
	
	private Date startBirthdate;

	private Date endBirthdate;

	private Integer minAge;
	
	private Integer maxAge;
	
	private String[] language;

	private String[] ethnic;

	private Address address;

	private Boolean email;

	private Boolean qaddress;


	public UserFilter() {
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	public Date getStartBirthdate() {
		return startBirthdate;
	}

	public void setStartBirthdate(Date startBirthdate) {
		this.startBirthdate = startBirthdate;
	}

	public Date getEndBirthdate() {
		return endBirthdate;
	}

	public void setEndBirthdate(Date endBirthdate) {
		this.endBirthdate = endBirthdate;
	}

	public Integer getMinAge() {
		return minAge;
	}

	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}

	public Integer getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

	public String[] getLanguage() {
		return language;
	}

	public void setLanguage(String[] language) {
		this.language = language;
	}

	public String[] getEthnic() {
		return ethnic;
	}

	public void setEthnic(String[] ethnic) {
		this.ethnic = ethnic;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Boolean getEmail() {
		return email;
	}

	public void setEmail(Boolean email) {
		this.email = email;
	}

	public Boolean getQaddress() {
		return qaddress;
	}

	public void setQaddress(Boolean qaddress) {
		this.qaddress = qaddress;
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
	 * @param groups the groups to set
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
	 * @param roles the roles to set
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	/**
	 * Get the value of property {@code permisssions}.
	 *
	 * @return the permisssions
	 */
	public List<String> getPermisssions() {
		return permisssions;
	}

	/**
	 * Set the value of property {@code permisssions}.
	 *
	 * @param permisssions the permisssions to set
	 */
	public void setPermisssions(List<String> permisssions) {
		this.permisssions = permisssions;
	}

	/* (non-Javadoc)
	 * @see org.einnovator.sso.client.modelx.UserOptions#toString(org.einnovator.util.model.ToStringCreator)
	 */
	@Override
	public ToStringCreator toString(ToStringCreator creator) {
		return super.toString(creator)
				.append("", q)
				.append("gender", gender)
				.append("startBirthdate", startBirthdate)
				.append("endBirthdate", endBirthdate)
				.append("minAge", minAge)
				.append("maxAge", maxAge)
				.append("language", language)
				.append("ethnic", ethnic)
				.append("address", !address.isEmpty() ? address : null)
				.append("email", email)
				.append("qaddress", qaddress);
				
	}
	
}
