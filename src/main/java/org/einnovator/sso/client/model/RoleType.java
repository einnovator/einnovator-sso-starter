package org.einnovator.sso.client.model;


/**
 * Enum for type of{@code Role}.
 *
 * <p>GLOBAL roles are platform wide roles.
 * <p>GROUP roles are scoped to individual {@code Groups}.
 * 
 * @author support@einnovator.org
 *
 */
public enum RoleType {
	GLOBAL("Global"),
	GROUP("Group")
	;
	
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
