package org.einnovator.sso.client.model;


public enum InvitationStatus {
	PENDING("Pending"),
	ACCEPTED("Accepted"),
	REJECTED("Rejected"),
	FAILED("Failed");
	
	private final String displayName;

	InvitationStatus(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	public static InvitationStatus parse(String s) {
		for (InvitationStatus e: InvitationStatus.class.getEnumConstants()) {
			if (e.toString().equalsIgnoreCase(s)) {
				return e;
			}
		}
		return null;
	}
}
