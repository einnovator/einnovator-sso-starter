package org.einnovator.sso.client.model;

public enum GenderType {
	MALE("Male"),
	FEMALE("Female"),
	OTHER("Other");
	
	private final String displayName;

	GenderType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
