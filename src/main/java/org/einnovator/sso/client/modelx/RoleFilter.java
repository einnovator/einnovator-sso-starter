package org.einnovator.sso.client.modelx;

import org.einnovator.sso.client.model.RoleType;

public class RoleFilter extends RoleOptions {

	private String q;
	
	private String name;
	
	private String orgId;
	
	private Boolean inherited;
	
	private Boolean builtin;
	
	private Boolean custom;
	
	private Boolean global;

	private String user;
	
	private RoleType type;

	public RoleFilter() {
	}
	
	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
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

	public Boolean getGlobal() {
		return global;
	}

	public void setGlobal(Boolean global) {
		this.global = global;
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

	public RoleType getType() {
		return type;
	}

	public void setType(RoleType type) {
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
				+ (q != null ? "q=" + q + ", " : "") 
				+ (orgId != null ? "orgId=" + orgId + ", " : "")
				+ (name != null ? "name=" + name + ", " : "") 
				+ (inherited != null ? "inherited=" + inherited + ", " : "")
				+ (builtin != null ? "builtin=" + builtin + ", " : "") 
				+ (global != null ? "global=" + global + ", " : "") 
				+ (custom != null ? "custom=" + custom : "")
				+ (user != null ? "user=" + user : "")
				+ (type != null ? "type=" + type : "")
				+ "]";
	}
	
	
	
}
