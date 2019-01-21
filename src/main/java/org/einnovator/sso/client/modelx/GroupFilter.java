package org.einnovator.sso.client.modelx;

import org.einnovator.sso.client.model.Address;
import org.einnovator.sso.client.model.GroupType;
import org.einnovator.util.model.ToStringCreator;

public class GroupFilter extends GroupOptions {


	public static final GroupFilter TREE = new GroupFilter(true);

	public static final GroupFilter OPERATIONS = new GroupFilter(true, GroupType.OPERATION);

	public static final GroupFilter TEAMS = new GroupFilter(true, GroupType.TEAM);

	private String q;

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

	public GroupFilter(Boolean tree) {
		super(tree);
	}

	public GroupFilter(Boolean tree, GroupType type) {
		super(tree, type);
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

	/* (non-Javadoc)
	 * @see org.einnovator.sso.client.modelx.GroupOptions#toString0(org.einnovator.util.model.ToStringCreator)
	 */
	@Override
	public ToStringCreator toString(ToStringCreator creator) {
		return super.toString(creator)
				.append("owner", owner)
				.append("parent", parent)
				.append("q", q)
				.append("featured", featured)
				.append("hidden", hidden)
				.append("sub", sub)
				.append("address", !address.isEmpty() ? address : null);
	}
	
}
