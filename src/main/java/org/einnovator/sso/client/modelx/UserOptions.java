package org.einnovator.sso.client.modelx;

import org.einnovator.util.model.ObjectBase;

public class UserOptions extends ObjectBase {

	public static final UserOptions ORGS = new UserOptions(true, false, false, false, false);

	public static final UserOptions ORGS_OPS = new UserOptions(true, true, false, false, false);

	public static final UserOptions ORGS_OPS_TEAMS = new UserOptions(true, true, true, false, false);

	public static final UserOptions FULL = new UserOptions(true, true, true, true, true);

	public static final UserOptions DEFAULT_OPTIONS = ORGS;

	private Boolean orgs;

	private Boolean ops;

	private Boolean teams;

	private Boolean roles;

	private Boolean permissions;

	public UserOptions() {
	}

	public UserOptions(Boolean orgs, Boolean ops, Boolean teams, Boolean roles, Boolean permissions) {
		super();
		this.orgs = orgs;
		this.ops = ops;
		this.teams = teams;
		this.roles = roles;
		this.permissions = permissions;
	}


	public Boolean getOrgs() {
		return orgs;
	}

	public void setOrgs(Boolean orgs) {
		this.orgs = orgs;
	}

	public Boolean getOps() {
		return ops;
	}

	public void setOps(Boolean ops) {
		this.ops = ops;
	}

	public Boolean getTeams() {
		return teams;
	}

	public void setTeams(Boolean teams) {
		this.teams = teams;
	}

	public Boolean getRoles() {
		return roles;
	}

	public void setRoles(Boolean roles) {
		this.roles = roles;
	}

	public Boolean getPermissions() {
		return permissions;
	}

	public void setPermissions(Boolean permissions) {
		this.permissions = permissions;
	}
	

}
