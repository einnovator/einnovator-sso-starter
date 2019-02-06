package org.einnovator.sso.client.model;


import org.einnovator.util.model.ObjectBase;
import org.einnovator.util.model.ToStringCreator;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address extends ObjectBase {
	
	private String country;
	
	private String countryCode2;

	private String countryCode3;
	
	private String postalCode;
	
	private String city;
	
	private String line1;

	private String line2;

	private String state;

	private String locality;

	private String latitude;
	
	private String longitude;

	public Address() {
	}
	
	public Address(String country, String postalCode, String city, String line1, String line2, String state, String locality) {
		this.country = country;
		this.postalCode = postalCode;
		this.city = city;
		this.line1 = line1;
		this.line2 = line2;
		this.state = state;
		this.locality = locality;
		this.locality = locality;
	}

	public Address(String country, String state) {
		this.country = country;
		this.state = state;
	}

	public Address(Object obj) {
		updateFrom(obj);			
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountryCode2() {
		return countryCode2;
	}

	public void setCountryCode2(String countryCode2) {
		this.countryCode2 = countryCode2;
	}

	public String getCountryCode3() {
		return countryCode3;
	}

	public void setCountryCode3(String countryCode3) {
		this.countryCode3 = countryCode3;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public ToStringCreator toString0(ToStringCreator builder) {
		return builder
			.append("country", country)
			.append("countryCode2", countryCode2)
			.append("countryCode3", countryCode3)
			.append("postalCode", postalCode)
			.append("state", state)
			.append("city", city)
			.append("locality", locality)
			.append("line1", line1)
			.append("line2", line2)
			.append("latitude", latitude)
			.append("longitude", longitude);
	}

	@JsonIgnore
	public boolean isEmpty() {
		return !StringUtils.hasText(country) && !StringUtils.hasText(postalCode) && 
				!StringUtils.hasText(city) && !StringUtils.hasText(line1) && 
				!StringUtils.hasText(line2) && !StringUtils.hasText(state) &&
				!StringUtils.hasText(locality) &&
				!StringUtils.hasText(countryCode2) && !StringUtils.hasText(countryCode3) &&
				!StringUtils.hasText(latitude) && !StringUtils.hasText(longitude);
	}

	public String getFormatted() {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.hasText(city)) {
			sb.append(city);
		}
		if (StringUtils.hasText(state)) {
			if (sb.length()!=0) {
				sb.append(", ");
			}
			sb.append(state);
		}
		if (StringUtils.hasText(country)) {
			if (sb.length()!=0) {
				sb.append(", ");
			}
			sb.append(country);
		}
		return sb.toString();
	}

	public String getFormatted1() {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.hasText(line1)) {
			sb.append(line1.trim());
		}
		if (StringUtils.hasText(line2)) {
			if (sb.length()!=0) {
				if (!sb.toString().endsWith(",")) {
					sb.append(",");
				} else {
					sb.append(" ");
				}
			}
			sb.append(line2);
		}

		if (StringUtils.hasText(city)) {
			if (sb.length()!=0) {
				sb.append(", ");
			}
			sb.append(city.trim());
		}
		if (StringUtils.hasText(postalCode)) {
			if (sb.length()!=0) {
				sb.append(", ");
			}
			sb.append(postalCode.trim());
		}

		if (StringUtils.hasText(state)) {
			if (sb.length()!=0) {
				sb.append(", ");
			}
			sb.append(state.trim());
		}
		if (StringUtils.hasText(country)) {
			if (sb.length()!=0) {
				sb.append(", ");
			}
			sb.append(country.trim());
		}
		return sb.toString();
	}

	public String getFormatted2() {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.hasText(line1)) {
			sb.append(line1);
		}
		if (StringUtils.hasText(line2)) {
			if (sb.length()!=0) {
				sb.append(" ");
			}
			sb.append(line2);
		}

		if (StringUtils.hasText(city)) {
			if (sb.length()!=0) {
				sb.append(", ");
			}
			sb.append(city);
		}
		if (StringUtils.hasText(state)) {
			if (sb.length()!=0) {
				sb.append(", ");
			}
			sb.append(state);
		}
		if (StringUtils.hasText(country)) {
			if (sb.length()!=0) {
				sb.append(", ");
			}
			sb.append(country);
		}
		return sb.toString();
	}

	/**
	 * @param address
	 * @return
	 */
	public static boolean isEmpty(Address address) {
		return address==null || address.isEmpty();
	}

}
