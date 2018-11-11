package org.einnovator.sso.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.einnovator.util.model.EntityBase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

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
	
	public Member() {
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" 
				+ (user != null ? "username=" + user.getUsername() + ", " : "")
				+ (group != null ? "group=" + group.getId() + " " + group.getUuid() + " " + group.getName() + ", " : "")
				+ (title != null ? "title=" + title + ", " : "")
				+ (startDate != null ? "startDate=" + startDate + ", " : "")
				+ (enabled != null ? "enabled=" + enabled : "") 
				+ (user!=null ? "user=" + user : "")
				+ (roles!=null ? "roles=" + roles : "")
				+ "]";
	}

	public static List<User> toUsers(List<Member> members) {
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

	public static List<Group> toGroups(List<Member> members) {
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

	public static List<Group> toGroups(GroupType type, List<Member> members) {
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
