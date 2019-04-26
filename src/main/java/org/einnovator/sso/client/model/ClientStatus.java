package org.einnovator.sso.client.model;

public enum ClientStatus {
	ENABLED("Enabled"),
	DISABLED("Disabled"),
	;
	
	private String displayValue;
	
	private ClientStatus(String displayValue) {
		this.displayValue = displayValue;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}
	
	public static ClientStatus parse(String s) {
		for (ClientStatus e: ClientStatus.class.getEnumConstants()) {
			if (e.toString().equalsIgnoreCase(s)) {
				return e;
			}
		}
		return null;
	}
	
}