package org.einnovator.sso.client.modelx;

import java.util.ArrayList;
import java.util.List;

import org.einnovator.sso.client.model.GroupType;
import org.einnovator.sso.client.model.User;
import org.einnovator.util.model.EntityOptions;
import org.einnovator.util.model.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOptions extends EntityOptions<User> {

	public static final UserOptions ORGS = new UserOptions(true, false, false, false, false);

	public static final UserOptions ORGS_OPS = new UserOptions(true, true, false, false, false);

	public static final UserOptions ORGS_OPS_TEAMS = new UserOptions(true, true, true, false, false);

	public static final UserOptions FULL = new UserOptions(true, true, true, true, true);

	public static final UserOptions DEFAULT = ORGS;

	private Boolean orgs;

	private Boolean ops;

	private Boolean teams;

	private Boolean authorities;
	
	public UserOptions() {
	}

	public UserOptions(Boolean orgs, Boolean ops, Boolean teams, Boolean roles, Boolean authorities) {
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

	
	/**
	 * Get the value of property {@code authorities}.
	 *
	 * @return the authorities
	 */
	public Boolean getAuthorities() {
		return authorities;
	}

	/**
	 * Set the value of property {@code authorities}.
	 *
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(Boolean authorities) {
		this.authorities = authorities;
	}



	/* (non-Javadoc)
	 * @see org.einnovator.util.model.ObjectBase#toString(org.einnovator.util.model.ToStringCreator)
	 */
	@Override
	public ToStringCreator toString(ToStringCreator creator) {
		return super.toString(creator
				.append("orgs", orgs)
				.append("ops", ops)
				.append("teams", teams)
				.append("authorities", authorities)
				);
	}
	
	@JsonIgnore
	public List<GroupType> getTypes() {
		ArrayList<GroupType> types = new ArrayList<>();
		if (Boolean.TRUE.equals(orgs)) {
			types.add(GroupType.ORGANIZATION);
		}
		if (Boolean.TRUE.equals(ops)) {
			types.add(GroupType.OPERATION);
		}
		if (Boolean.TRUE.equals(teams)) {
			types.add(GroupType.TEAM);
		}
		return types;
	}

	public boolean allMembership() {
		return Boolean.TRUE.equals(orgs) && Boolean.TRUE.equals(ops) && Boolean.TRUE.equals(teams);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
		result = prime * result + ((ops == null) ? 0 : ops.hashCode());
		result = prime * result + ((orgs == null) ? 0 : orgs.hashCode());
		result = prime * result + ((teams == null) ? 0 : teams.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserOptions other = (UserOptions) obj;
		if (authorities == null) {
			if (other.authorities != null)
				return false;
		} else if (!authorities.equals(other.authorities))
			return false;
		if (ops == null) {
			if (other.ops != null)
				return false;
		} else if (!ops.equals(other.ops))
			return false;
		if (orgs == null) {
			if (other.orgs != null)
				return false;
		} else if (!orgs.equals(other.orgs))
			return false;
		if (teams == null) {
			if (other.teams != null)
				return false;
		} else if (!teams.equals(other.teams))
			return false;
		return true;
	}
	
	

}
