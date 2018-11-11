package org.einnovator.sso.client.model;


public enum UserType {
	CONSUMER("Consumer"),	
	ORGANIZATIONAL("Organizational"),
	EMPLOYEE("Employee"),
	AFFILATED("Affiliated");
	
	private final String displayName;

	UserType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	public static UserType parse(String s) {
		for (UserType e: UserType.class.getEnumConstants()) {
			if (e.toString().equalsIgnoreCase(s)) {
				return e;
			}
		}
		return null;
	}
}
