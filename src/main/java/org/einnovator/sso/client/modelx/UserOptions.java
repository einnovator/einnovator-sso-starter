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

/**
 * Options for {@code User} lookup and operations.
 * 
 * @see UserFilter
 * @see org.einnovator.sso.client.manager.UserManager
 * @see org.einnovator.sso.client.model.User
 * @author support@einnovator.org
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOptions extends EntityOptions<User> {

	public static final UserOptions ORGS = new UserOptions(true, false, false, false);

	public static final UserOptions ORGS_OPS = new UserOptions(true, true, false, false);

	public static final UserOptions ORGS_OPS_TEAMS = new UserOptions(true, true, true, false);

	public static final UserOptions FULL = new UserOptions(true, true, true, true);

	public static final UserOptions DEFAULT = ORGS;

	private Boolean orgs;

	private Boolean ops;

	private Boolean teams;

	private Boolean notify;

	private Boolean sms;

	private Boolean code;
	
	//
	// Constructors
	//

	/**
	 * Create instance of {@code UserOptions}.
	 *
	 */
	public UserOptions() {
	}

	/**
	 * Create instance of {@code UserOptions}.
	 * 
	 * @param orgs fetch group membership in organizations
	 * @param teams fetch group membership in teams
	 * @param roles fetch roles

	 * 
	 */
	public UserOptions(Boolean orgs, Boolean ops, Boolean teams, Boolean roles) {
		super();
		this.orgs = orgs;
		this.ops = ops;
		this.teams = teams;
	}

	//
	// Getter/Setter
	//
	
	/**
	 * Get the value of property {@code orgs}.
	 *
	 * @return the value of {@code orgs}
	 */
	public Boolean getOrgs() {
		return orgs;
	}

	/**
	 * Set the value of property {@code orgs}.
	 *
	 * @param orgs the value of {@code orgs}
	 */
	public void setOrgs(Boolean orgs) {
		this.orgs = orgs;
	}

	/**
	 * Get the value of property {@code ops}.
	 *
	 * @return the value of {@code ops}
	 */
	public Boolean getOps() {
		return ops;
	}

	/**
	 * Set the value of property {@code ops}.
	 *
	 * @param ops the value of {@code ops}
	 */
	public void setOps(Boolean ops) {
		this.ops = ops;
	}

	/**
	 * Get the value of property {@code teams}.
	 *
	 * @return the value of {@code teams}
	 */
	public Boolean getTeams() {
		return teams;
	}

	/**
	 * Set the value of property {@code teams}.
	 *
	 * @param teams the value of {@code teams}
	 */
	public void setTeams(Boolean teams) {
		this.teams = teams;
	}

	/**
	 * Get the value of property {@code notify}.
	 *
	 * @return the value of {@code notify}
	 */
	public Boolean getNotify() {
		return notify;
	}

	/**
	 * Set the value of property {@code notify}.
	 *
	 * @param notify the value of {@code notify}
	 */
	public void setNotify(Boolean notify) {
		this.notify = notify;
	}

	/**
	 * Get the value of property {@code sms}.
	 *
	 * @return the value of {@code sms}
	 */
	public Boolean getSms() {
		return sms;
	}

	/**
	 * Set the value of property {@code sms}.
	 *
	 * @param sms the value of {@code sms}
	 */
	public void setSms(Boolean sms) {
		this.sms = sms;
	}

	/**
	 * Get the value of property {@code code}.
	 *
	 * @return the value of {@code code}
	 */
	public Boolean getCode() {
		return code;
	}

	/**
	 * Set the value of property {@code code}.
	 *
	 * @param code the value of {@code code}
	 */
	public void setCode(Boolean code) {
		this.code = code;
	}


	//
	// With
	//
	
	/**
	 * Set the value of property {@code orgs}.
	 *
	 * @param orgs the value of {@code orgs}
	 * @return this
	 */
	public UserOptions withOrgs(Boolean orgs) {
		this.orgs = orgs;
		return this;
	}

	/**
	 * Set the value of property {@code ops}.
	 *
	 * @param ops the value of {@code ops}
	 * @return this
	 */
	public UserOptions withOps(Boolean ops) {
		this.ops = ops;
		return this;
	}

	/**
	 * Set the value of property {@code teams}.
	 *
	 * @param teams the value of {@code teams}
	 * @return this
	 */
	public UserOptions withTeams(Boolean teams) {
		this.teams = teams;
		return this;
	}

	/**
	 * Set the value of property {@code notify}.
	 *
	 * @param notify the value of {@code notify}
	 * @return this
	 */
	public UserOptions withNotify(Boolean notify) {
		this.notify = notify;
		return this;
	}

	/**
	 * Set the value of property {@code sms}.
	 *
	 * @param sms the value of {@code sms}
	 * @return this
	 */
	public UserOptions withSms(Boolean sms) {
		this.sms = sms;
		return this;
	}

	/**
	 * Set the value of property {@code code}.
	 *
	 * @param code the value of {@code code}
	 * @return this
	 */
	public UserOptions withCode(Boolean code) {
		this.code = code;
		return this;
	}

	
	@Override
	public ToStringCreator toString(ToStringCreator creator) {
		return super.toString(creator
				.append("orgs", orgs)
				.append("ops", ops)
				.append("teams", teams)
				.append("notify", notify)
				.append("sms", sms)
				.append("code", code)
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
