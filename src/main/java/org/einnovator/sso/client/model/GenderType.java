package org.einnovator.sso.client.model;

/**
 * A Enum for {@code Gender}.
 *
 * @author support@einnovator.org
 *
 */
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
