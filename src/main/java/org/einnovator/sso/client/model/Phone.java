package org.einnovator.sso.client.model;

import org.einnovator.util.model.ObjectBase;
import org.einnovator.util.model.ToStringCreator;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Phone extends ObjectBase {

	protected String number;

	protected String area;

	protected String countryCode;

	protected String country;

	public Phone() {
	}

	public Phone(Object obj) {
		super(obj);
	}
	
	public Phone(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public ToStringCreator toString0(ToStringCreator builder) {
		return builder
			.append("number", number)
			.append("area", area)
			.append("countryCode", countryCode)
			.append("country", country);
	}
	
	@JsonIgnore
	public boolean isEmpty() {
		return !StringUtils.hasText(country) &&
				!StringUtils.hasText(countryCode) &&
				!StringUtils.hasText(number) && 
				!StringUtils.hasText(area);
	}

	public String getFormatted() {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.hasText(countryCode)) {
			if (!countryCode.trim().startsWith("+")) {
				sb.append("+");
			}
			sb.append(countryCode);
		}
		if (StringUtils.hasText(area)) {
			if (sb.length()!=0) {
				sb.append(" ");
			}
			sb.append("(");
			sb.append(area);
			sb.append(")");
		}

		if (StringUtils.hasText(number)) {
			if (sb.length()!=0) {
				sb.append(" ");
			}
			sb.append(number);
		}

		return sb.toString();
	}

	
}
