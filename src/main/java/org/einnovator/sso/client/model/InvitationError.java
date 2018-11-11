package org.einnovator.sso.client.model;


public enum InvitationError {
	ALREADY_INVITED("invitation.error.already_invited", "Already invited"),
	USER_EXITS("invitation.error.user_exists", "User already registered"),
	SELF_USER("invitation.error.self", "Can not invite youself"),
	SELF_GROUP("invitation.error.self_group", "Can not invite self group"),
	SELF_SUBGROUP("invitation.error.self_group", "Can not invite sub-ordinate groups"),
	MAIL_ERROR("invitation.error.mail", "Mail Send Error"),
	SMS_ERROR("invitation.error.sms", "SMS Send Error");
	
	private final String code;

	private final String message; //default

	InvitationError(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public static InvitationError parse(String s) {
		if (s!=null) {
			s = s.replace('-', '_');			
			for (InvitationError e: InvitationError.class.getEnumConstants()) {
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
	
}
