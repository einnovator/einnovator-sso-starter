package org.einnovator.sso.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.einnovator.util.model.EntityBase;
import org.einnovator.util.model.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A Group {@code Member}.
 *
 * @author support@einnovator.org
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member extends EntityBase {

	private User user;
	
	private Group group;
	
	private String title;
	
	private Date startDate;
	
	private Date endDate;
	
	private Boolean enabled;
	
	private List<Role> roles;
	
	/**
	 * Create instance of {@code Member}.
	 *
	 */
	public Member() {
	}

	/**
	 * Create instance of {@code Member}.
	 *
	 * @param prototype a prototype
	 */
	public Member(Object prototype) {
		super(prototype);
	}
	/**
	 * Get the value of property {@code user}.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Set the value of property {@code user}.
	 *
	 * @param user the value of property user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Get the value of property {@code group}.
	 *
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * Set the value of property {@code group}.
	 *
	 * @param group the value of property group
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * Get the value of property {@code title}.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the value of property {@code title}.
	 *
	 * @param title the value of property title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the value of property {@code startDate}.
	 *
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Set the value of property {@code startDate}.
	 *
	 * @param startDate the value of property startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Get the value of property {@code endDate}.
	 *
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Set the value of property {@code endDate}.
	 *
	 * @param endDate the value of property endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Get the value of property {@code enabled}.
	 *
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * Set the value of property {@code enabled}.
	 *
	 * @param enabled the value of property enabled
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Get the value of property {@code roles}.
	 *
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * Set the value of property {@code roles}.
	 *
	 * @param roles the value of property roles
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	//
	// With
	//
	
	/**
	 * Set the value of property {@code user}.
	 *
	 * @param user the value of property user
	 * @return this {@code Member}
	 */
	public Member withUser(User user) {
		this.user = user;
		return this;
	}

	/**
	 * Set the value of property {@code group}.
	 *
	 * @param group the value of property group
	 * @return this {@code Member}
	 */
	public Member withGroup(Group group) {
		this.group = group;
		return this;
	}

	/**
	 * Set the value of property {@code title}.
	 *
	 * @param title the value of property title
	 * @return this {@code Member}
	 */
	public Member withTitle(String title) {
		this.title = title;
		return this;
	}


	/**
	 * Set the value of property {@code startDate}.
	 *
	 * @param startDate the value of property startDate
	 * @return this {@code Member}
	 */
	public Member withStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}

	/**
	 * Set the value of property {@code endDate}.
	 *
	 * @param endDate the value of property endDate
	 * @return this {@code Member}
	 */
	public Member withEndDate(Date endDate) {
		this.endDate = endDate;
		return this;
	}

	/**
	 * Set the value of property {@code enabled}.
	 *
	 * @param enabled the value of property enabled
	 * @return this {@code Member}
	 */
	public Member withEnabled(Boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	/**
	 * Set the value of property {@code roles}.
	 *
	 * @param roles the value of property roles
	 * @return this {@code Member}
	 */
	public Member withRoles(List<Role> roles) {
		this.roles = roles;
		return this;
	}


	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
				.append("username", user!=null ? user.getUsername() : null)
				.append("group", group != null ? group.getUuid() + " " + group.getName() : null)
				.append("title", title)
				.append("startDate", startDate)
				.append("endDate", endDate)
				.append("enabled", enabled)
				.append("roles", roles)
				;
	}
	
	//
	// Static utils
	//
	
	public static List<User> getUsers(List<Member> members) {
		if (members==null) {
			return null;
		}
		List<User> users = new ArrayList<>();
		for (Member member: members) {
			if (member.getUser()!=null) {
				users.add(member.getUser());				
			}
		}
		return users;
	}

	public static List<Group> getGroups(List<Member> members) {
		if (members==null) {
			return null;
		}
		List<Group> groups = new ArrayList<>();
		for (Member member: members) {
			if (member.getGroup()!=null) {
				groups.add(member.getGroup());				
			}
		}
		return groups;
	}

	public static List<Group> getGroups(GroupType type, List<Member> members) {
		if (members==null) {
			return null;
		}
		List<Group> groups = new ArrayList<>();
		for (Member member: members) {
			if (member.getGroup()!=null && type==member.getGroup().getType()) {
				groups.add(member.getGroup());				
			}
		}
		return groups;
	}

}
