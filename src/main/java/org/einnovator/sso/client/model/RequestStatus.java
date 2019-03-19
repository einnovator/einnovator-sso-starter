package org.einnovator.sso.client.model;


public enum RequestStatus {
	PENDING("Pending"),
	ACCEPTED("Accepted"),
	REJECTED("Rejected");
	
	private final String displayValue;

	RequestStatus(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public String getDisplayName() {
		return displayValue;
	}

	public static RequestStatus parse(String s) {
		for (RequestStatus e: RequestStatus.class.getEnumConstants()) {
			if (e.toString().equalsIgnoreCase(s)) {
				return e;
			}
		}
		return null;
	}
}
