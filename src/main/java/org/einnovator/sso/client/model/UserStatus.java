package org.einnovator.sso.client.model;

public enum UserStatus {
	UNCONFIRMED("Unconfirmed"), //User has been created but not confirmed
	CONFIRMED("Confirmed"), // User has been confirmed
	FORCE_CHANGE_PASSWORD("Force Change Password"), 
	ARCHIVED("Archived"), // User is no longer active.
	COMPROMISED("Compromised"), //User is disabled due to a potential security threat
	RESET_REQUIRED("Reset Required"), //Reset required
	UNKNOWN("Unknow"); // User status is not known

	private String displayValue;
	
	private UserStatus(String displayValue) {
		this.displayValue = displayValue;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}
	
	public static UserStatus parse(String s) {
		for (UserStatus e: UserStatus.class.getEnumConstants()) {
			if (e.toString().equalsIgnoreCase(s)) {
				return e;
			}
		}
		return null;
	}
	
}