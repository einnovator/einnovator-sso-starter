package org.einnovator.sso.client.model;


public enum RoleType {
	GLOBAL("Global"),
	GROUP("Operation");
	
	private final String displayName;

	RoleType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	public String getDisplayValue() {
		return displayName;
	}
	
	public static RoleType parse(String s) {
		for (RoleType e: RoleType.class.getEnumConstants()) {
			if (e.toString().equalsIgnoreCase(s)) {
				return e;
			}
		}
		return null;
	}
}
