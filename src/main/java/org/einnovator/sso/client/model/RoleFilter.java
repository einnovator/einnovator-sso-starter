package org.einnovator.sso.client.model;

public class RoleFilter {

	private String q;
	
	private String name;

	private String orgId;
	
	private Boolean inherited;
	
	private RoleType type;
	
	private Boolean builtin;

	private Boolean global;

	private Boolean custom;
	
	private String user;
	
	public RoleFilter() {
	}
	
	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoleType getType() {
		return type;
	}

	public void setType(RoleType type) {
		this.type = type;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Boolean getInherited() {
		return inherited;
	}

	public void setInherited(Boolean inherited) {
		this.inherited = inherited;
	}

	public Boolean getBuiltin() {
		return builtin;
	}

	public void setBuiltin(Boolean builtin) {
		this.builtin = builtin;
	}

	public Boolean getCustom() {
		return custom;
	}

	public void setCustom(Boolean custom) {
		this.custom = custom;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Boolean getGlobal() {
		return global;
	}

	public void setGlobal(Boolean global) {
		this.global = global;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" 
				+ (q != null ? "q=" + q + ", " : "") 
				+ (name != null ? "name=" + name + ", " : "")
				+ (orgId != null ? "orgId=" + orgId + ", " : "")
				+ (type != null ? "type=" + type : "")				
				+ (inherited != null ? "inherited=" + inherited + ", " : "")
				+ (builtin != null ? "builtin=" + builtin + ", " : "") 
				+ (global != null ? "global=" + global + ", " : "") 
				+ (custom != null ? "custom=" + custom : "")
				+ (user != null ? "user=" + user : "")
				+ "]";
	}
	
	
	
}
