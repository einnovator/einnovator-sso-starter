package org.einnovator.sso.client.model;


public enum InvitationError {
	ALREADY_INVITED("invitation.error.already_invited", "Already invited"),
	USER_EXITS("invitation.error.user_exists", "User already registered"),
	ALREADY_MEMBER("invitation.error.already_member", "User already member of Group"),
	SELF("invitation.error.self", "Can not invite self"),
	GROUP_CONNECT("invitation.error.group", "Can not invite and connect with group"),
	MAIL_ERROR("invitation.error.mail", "Mail Send Error"),
	SMS_ERROR("invitation.error.sms", "SMS Send Error");
	
	private final String code;

	private final String message; //default message

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
