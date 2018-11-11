package org.einnovator.sso.client.model;


public class AddressBuilder {
	
	protected String country;
	
	protected String countryCode2;
	
	protected String countryCode3;
	
	protected String postalCode;
	
	protected String city;
	
	protected String line1;

	protected String line2;

	protected String state;
	
	protected String locality;
	
	protected String latitude;
	
	protected String longitude;

	public AddressBuilder() {
	}
	
	public AddressBuilder country(String country) {
		this.country = country;
		return this;
	}

	public AddressBuilder postalCode(String postalCode) {
		this.postalCode = postalCode;
		return this;
	}

	public AddressBuilder city(String city) {
		this.city = city;
		return this;
	}
	
	public AddressBuilder line1(String line1) {
		this.line1 = line1;
		return this;
	}

	public AddressBuilder line2(String line2) {
		this.line2 = line2;
		return this;
	}
	
	public AddressBuilder state(String state) {
		this.state = state;
		return this;
	}

	public AddressBuilder locality(String locality) {
		this.locality = locality;
		return this;
	}

	public AddressBuilder countryCode2(String countryCode2) {
		this.countryCode2 = countryCode2;
		return this;
	}

	public AddressBuilder countryCode3(String countryCode3) {
		this.countryCode3 = countryCode3;
		return this;
	}

	public AddressBuilder latitude(String latitude) {
		this.latitude = latitude;
		return this;
	}

	public AddressBuilder longitude(String longitude) {
		this.longitude = longitude;
		return this;
	}

	
	public Address build() {
		Address address = new Address();
		address.setCountry(country);
		address.setCountryCode2(countryCode2);
		address.setCountryCode3(countryCode3);
		address.setCity(city);
		address.setState(state);
		address.setLocality(locality);
		address.setLine1(line1);
		address.setLine2(line2);
		address.setPostalCode(postalCode);
		address.setLatitude(latitude);
		address.setLongitude(longitude);
		return address;
	}

}
