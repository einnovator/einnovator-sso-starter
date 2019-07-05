package org.einnovator.sso.client.model;


public enum InvitationType {
	USER("User"),
	MEMBER("Member"),
	CONNECTION("Connection"),
	GROUP_CONNECTION("Group Connection"),
	OTHER("Other");
	
	private final String displayValue;

	InvitationType(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayName() {
		return displayValue;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}
	
	public static InvitationType parse(String s) {
		if (s!=null) {
			for (InvitationType e: InvitationType.class.getEnumConstants()) {
				String key = e.toString();
				if (key.equalsIgnoreCase(s)) {
					return e;
				}
			}
		}
		return null;
	}


}
