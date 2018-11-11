package org.einnovator.sso.client.model;


public enum InvitationType {
	USER("User"),
	EMPLOYEE("Employee"),
	MEMBER("Member"),
	SUPPLIER("Supplier"),
	XSUPPLIER("Indirect Supplier"),
	CUSTOMER("Customer"),
	WALLET("Wallet"),
	CONNECTION("Connection");
	
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
			s = s.replace('-', '_');			
			for (InvitationType e: InvitationType.class.getEnumConstants()) {
				String key = e.toString();
				if (key.equalsIgnoreCase(s)) {
					return e;
				}
				key = key.replaceAll("_", "");
				if (key.equalsIgnoreCase(s)) {
					return e;
				}
			}
		}
		return null;
	}

	public static InvitationType getDefault(GroupType groupType) {
		switch (groupType) {
		case ORGANIZATION: case OPERATION:	return InvitationType.EMPLOYEE;
		default: return InvitationType.MEMBER;
		}
	}
	
}
