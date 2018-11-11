package org.einnovator.sso.client.model;

import java.util.List;

import org.einnovator.util.model.EntityBase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Role extends EntityBase {	

	private String name;
	
	private RoleType type;

	private String description;

	private String owner;
		
	private Integer userCount;
	
	private Boolean builtIn;
	
	private Boolean global;
	
	private List<Permission> permissions;
	
	private Group group;

	
	public Role() {
	}
	
	public Role(String name, Boolean builtIn) {
		this.name = name;
		this.builtIn = builtIn;
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

	public Boolean getBuiltIn() {
		return builtIn;
	}

	public void setBuiltIn(Boolean builtIn) {
		this.builtIn = builtIn;
	}
	
	public Boolean getGlobal() {
		return global;
	}

	public void setGlobal(Boolean global) {
		this.global = global;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
	public Permission findPermission(String id) {
		if (permissions!=null) {
			for (Permission perm: permissions) {
				if (id.equals(perm.getUuid()) || id.equals((perm.getKey()))) {
					return perm;
				}
			}
		}
		return null;
	}
		

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" 
				+ (getId() != null ? "id=" + getId() + ", " : "") 
				+ (name != null ? "name=" + name + ", " : "")
				+ (builtIn != null ? "builtIn=" + builtIn+ ", "  : "") 
				+ (global != null ? "global=" + global+ ", "  : "") 
				+ (owner != null ? "owner=" + owner+ ", "  : "") 
				+ (userCount != null ? "userCount=" + userCount+ ", "  : "") 
				+ (group != null ? "group=" + group+ ", "  : "") 
				+ (description != null ? "description=" + description : "") 
				+ (permissions != null ? "permissions=" + permissions : "") 
				+ "]";
	}

}
