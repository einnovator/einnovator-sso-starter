package org.einnovator.sso.client.model;


public class Trigger {

	public static final String TRIGGER_ACCEPT = "accept";
	public static final String TRIGGER_REJECT = "reject";
	
	private String type;
	
	private String name;

	public Trigger() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" 
			+ (type != null ? "type=" + type + ", " : "") 
			+ (name != null ? "name=" + name : "") 
			+ "]";
	}

	
}
