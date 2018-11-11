package org.einnovator.sso.client.modelx;

import org.einnovator.sso.client.model.Address;
import org.einnovator.sso.client.model.GroupType;

public class GroupFilter {

	private String q;

	private GroupType type;
	
	private String owner;

	private String parent;

	private String root;

	private Boolean featured;
	
	private Boolean hidden;

	private Boolean selected;

	private Boolean profile;

	private Boolean sub;

	private Address address;

	public GroupFilter() {
	}


	public GroupType getType() {
		return type;
	}

	public void setType(GroupType type) {
		this.type = type;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Boolean getProfile() {
		return profile;
	}

	public void setProfile(Boolean profile) {
		this.profile = profile;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
	
	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public Boolean getFeatured() {
		return featured;
	}

	public void setFeatured(Boolean featured) {
		this.featured = featured;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public Boolean getSub() {
		return sub;
	}

	public void setSub(Boolean sub) {
		this.sub = sub;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" 
				+ (owner != null ? "owner=" + owner + ", " : "")
				+ (parent != null ? "parent=" + parent + ", " : "") 
				+ (root != null ? "root=" + root + ", " : "") 
				+ (q != null ? "q=" + q + ", " : "")
				+ (featured != null ? "featured=" + featured : "")
				+ (hidden != null ? "hidden=" + hidden : "")
				+ (sub != null ? "sub=" + sub : "")
				+ (address != null && !address.isEmpty() ? "address=" + address : "")
				+ "]";
	}
	
}
