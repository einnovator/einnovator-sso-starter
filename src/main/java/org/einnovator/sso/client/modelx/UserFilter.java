package org.einnovator.sso.client.modelx;

import java.util.Arrays;
import java.util.Date;

import org.einnovator.sso.client.model.Address;
import org.einnovator.sso.client.model.GenderType;

public class UserFilter extends UserOptions {

	private String q;
	
	private String groupId;
	
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

	@Override
	public String toString() {
		return "UserFilter [" 
				+ (q != null ? "q=" + q + ", " : "")
				+ (gender != null ? "gender=" + gender + ", " : "")
				+ (startBirthdate != null ? "startBirthdate=" + startBirthdate + ", " : "")
				+ (endBirthdate != null ? "endBirthdate=" + endBirthdate + ", " : "")
				+ (minAge != null ? "minAge=" + minAge + ", " : "") 
				+ (maxAge != null ? "maxAge=" + maxAge + ", " : "")
				+ (language != null ? "language=" + Arrays.toString(language) + ", " : "")
				+ (ethnic != null ? "ethnic=" + Arrays.toString(ethnic) + ", " : "")
				+ (address != null && !address.isEmpty() ? "address=" + address : "") 
				+ (email != null ? "email=" + email + ", " : "")
				+ (qaddress != null ? "qaddress=" + qaddress + ", " : "")
				+ (email != null ? "email=" + email + ", " : "")
				+ (qaddress != null ? "qaddress=" + qaddress + ", " : "")
				+ "]";
	}
	
}
