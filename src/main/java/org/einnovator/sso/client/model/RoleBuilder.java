package org.einnovator.sso.client.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoleBuilder {	

	private String name;
	
	private RoleType type;

	private String description;

	private String owner;
		
	private Boolean builtIn;
	
	private Boolean global;
	
	private List<Permission> permissions;
	
	private Group group;

	
	public RoleBuilder() {
	}
	
	public RoleBuilder name(String name) {
		this.name = name;
		return this;
	}	

	public RoleBuilder type(RoleType type) {
		this.type = type;
		return this;
	}

	public RoleBuilder builtIn(Boolean builtIn) {
		this.builtIn = builtIn;
		return this;
	}

	public RoleBuilder global(Boolean global) {
		this.global = global;
		return this;
	}

	public RoleBuilder description(String description) {
		this.description = description;
		return this;
	}

	public RoleBuilder owner(String owner) {
		this.owner = owner;
		return this;
	}

	public RoleBuilder group(Group group) {
		this.group = group;
		return this;
	}

	public RoleBuilder permissions(List<Permission> permissions) {
		this.permissions = permissions;
		return this;
	}

	public RoleBuilder permissions(Permission... permissions) {
		if (this.permissions==null) {
			this.permissions = new ArrayList<>();
		}
		this.permissions.addAll(Arrays.asList(permissions));
		return this;
	}


	public Role build() {
		Role role = new Role();
		role.setName(name);
		role.setType(type);
		role.setGlobal(global);
		role.setBuiltIn(builtIn);
		role.setGroup(group);
		role.setPermissions(permissions);
		role.setOwner(owner);
		role.setDescription(description);
		return role;
	}
}
