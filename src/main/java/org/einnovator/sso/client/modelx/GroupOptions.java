package org.einnovator.sso.client.modelx;

import org.einnovator.sso.client.model.GroupType;
import org.einnovator.util.model.ObjectBase;
import org.einnovator.util.model.ToStringCreator;

public class GroupOptions extends ObjectBase {

	private GroupType type;
	
	private Boolean tree;

	public GroupOptions() {
	}

	public GroupOptions(Boolean tree) {
		this.tree = tree;
	}

	public GroupOptions(Boolean tree, GroupType type) {
		this.tree = tree;
		this.type = type; 
	}

	public GroupType getType() {
		return type;
	}

	public void setType(GroupType type) {
		this.type = type;
	}

	/**
	 * Get the value of property {@code tree}.
	 *
	 * @return the tree
	 */
	public Boolean getTree() {
		return tree;
	}

	/**
	 * Set the value of property {@code tree}.
	 *
	 * @param tree the tree to set
	 */
	public void setTree(Boolean tree) {
		this.tree = tree;
	}

	/* (non-Javadoc)
	 * @see org.einnovator.util.model.ObjectBase#toString0(org.einnovator.util.model.ToStringCreator)
	 */
	@Override
	public ToStringCreator toString(ToStringCreator creator) {
		return super.toString(creator)
				.append("type", type)
				.append("tree", tree);
	}
}
