package org.einnovator.sso.client.model;


public enum RequestType {
	ROLE("Role"),
	MEMBER("Member");
	
	private final String displayValue;

	RequestType(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public String getDisplayName() {
		return displayValue;
	}

	public static RequestType parse(String s) {
		for (RequestType e: RequestType.class.getEnumConstants()) {
			if (e.toString().equalsIgnoreCase(s)) {
				return e;
			}
		}
		return null;
	}
}
