package org.einnovator.sso.client.model;


public enum GroupType {
	ORGANIZATION("Organization"),
	OPERATION("Operation"),
	TEAM("Team"),
	FOLLOWS("Follows"),
	FOLLOWER("Follower"),
	CONTACTS("Contacts");
	
	private final String displayValue;

	GroupType(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayName() {
		return displayValue;
	}
	
	public static GroupType parse(String s) {
		for (GroupType e: GroupType.class.getEnumConstants()) {
			if (e.toString().equalsIgnoreCase(s)) {
				return e;
			}
		}
		return null;
	}
}
