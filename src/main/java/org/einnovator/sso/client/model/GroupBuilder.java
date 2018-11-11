package org.einnovator.sso.client.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class GroupBuilder {
	
	private String uuid;
		
	private String name;
	
	private GroupType type;
	
	private String owner;
	
	private String parentId;
	
	private String rootId;
	
	private String avatar;
	
	private String cover;

	private String cover2;

	private String website;
	
	private String social;

	private String social2;
	
	private Address address;

	private String description;
	
	private Map<String, Object> profile = new LinkedHashMap<>();


	public GroupBuilder() {
	}

	public GroupBuilder uuid(String uuid) {
		this.uuid = uuid;
		return this;
	}

	public GroupBuilder name(String name) {
		this.name = name;
		return this;
	}

	public GroupBuilder type(GroupType type) {
		this.type = type;
		return this;
	}

	public GroupBuilder parentId(String parentId) {
		this.parentId = parentId;
		return this;
	}

	public GroupBuilder rootId(String rootId) {
		this.rootId = rootId;
		return this;
	}

	public GroupBuilder owner(String owner) {
		this.owner = owner;
		return this;
	}

	
	public GroupBuilder avatar(String avatar) {
		this.avatar = avatar;
		return this;
	}

	public GroupBuilder description(String description) {
		this.description = description;
		return this;
	}

	public GroupBuilder address(Address address) {
		this.address = address;
		return this;
	}

	public GroupBuilder profile(Map<String, Object> profile) {
		this.profile = profile;
		return this;
	}
	

	public Group build() {
		Group group = new Group();
		group.setUuid(uuid);
		group.setName(name);
		group.setType(type);
		group.setOwner(owner);
		group.setParentId(parentId);
		group.setRootId(rootId);
		group.setWebsite(website);
		group.setSocial(social);
		group.setSocial2(social2);
		group.setAvatar(avatar);
		group.setCover(cover);
		group.setCover2(cover2);
		group.setAddress(address);
		group.setProfile(profile);
		group.setDescription(description);

		return group;
	}

}
