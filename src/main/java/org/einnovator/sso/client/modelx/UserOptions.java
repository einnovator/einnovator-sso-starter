package org.einnovator.sso.client.modelx;

import org.einnovator.util.model.ObjectBase;
import org.einnovator.util.model.ToStringCreator;

public class UserOptions extends ObjectBase {

	public static final UserOptions ORGS = new UserOptions(true, false, false, false);

	public static final UserOptions ORGS_OPS = new UserOptions(true, true, false, false);

	public static final UserOptions ORGS_OPS_TEAMS = new UserOptions(true, true, true, false);

	public static final UserOptions FULL = new UserOptions(true, true, true, true);

	public static final UserOptions DEFAULT_OPTIONS = FULL;

	private Boolean orgs;

	private Boolean ops;

	private Boolean teams;

	private Boolean authorities;

	public UserOptions() {
	}

	public UserOptions(Boolean orgs, Boolean ops, Boolean teams, Boolean authorities) {
		super();
		this.orgs = orgs;
		this.ops = ops;
		this.teams = teams;
		this.authorities = authorities;
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

	public Boolean getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Boolean authorities) {
		this.authorities = authorities;
	}

	
	/* (non-Javadoc)
	 * @see org.einnovator.util.model.ObjectBase#toString(org.einnovator.util.model.ToStringCreator)
	 */
	@Override
	public ToStringCreator toString(ToStringCreator creator) {
		return super.toString(creator)
				.append("orgs", orgs)
				.append("ops", ops)
				.append("teams", teams)
				.append("authorities", authorities)
				;				
	}
	
}
