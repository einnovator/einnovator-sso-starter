package org.einnovator.sso.client.modelx;

import java.util.List;

import org.einnovator.sso.client.model.GroupType;
import org.einnovator.util.model.EntityOptions;
import org.einnovator.util.model.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupOptions extends EntityOptions {

	public static final GroupOptions TREE = new GroupOptions(true);

	public static final GroupOptions OPERATIONS = new GroupOptions(true, GroupType.OPERATION);

	public static final GroupOptions TEAMS = new GroupOptions(true, GroupType.TEAM);

	private GroupType type;

	private List<GroupType> types;	
	
	private Boolean orgs;

	private Boolean ops;

	private Boolean tree;
	
	public GroupOptions() {
	}

	public GroupOptions(boolean tree, GroupType type) {
		this.tree = true;
		this.type = type;
	}

	public GroupOptions(boolean tree) {
		this.tree = true;
	}
	
	/**
	 * Get the value of property {@code type}.
	 *
	 * @return the type
	 */
	public GroupType getType() {
		return type;
	}

	/**
	 * Set the value of property {@code type}.
	 *
	 * @param type the type to set
	 */
	public void setType(GroupType type) {
		this.type = type;
	}

	/**
	 * Get the value of property {@code types}.
	 *
	 * @return the types
	 */
	public List<GroupType> getTypes() {
		return types;
	}

	/**
	 * Set the value of property {@code types}.
	 *
	 * @param types the types to set
	 */
	public void setTypes(List<GroupType> types) {
		this.types = types;
	}
	
	/**
	 * Get the value of property {@code orgs}.
	 *
	 * @return the orgs
	 */
	public Boolean getOrgs() {
		return orgs;
	}

	/**
	 * Set the value of property {@code orgs}.
	 *
	 * @param orgs the orgs to set
	 */
	public void setOrgs(Boolean orgs) {
		this.orgs = orgs;
	}

	/**
	 * Get the value of property {@code ops}.
	 *
	 * @return the ops
	 */
	public Boolean getOps() {
		return ops;
	}

	/**
	 * Set the value of property {@code ops}.
	 *
	 * @param ops the ops to set
	 */
	public void setOps(Boolean ops) {
		this.ops = ops;
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
	 * @see org.einnovator.util.model.ObjectBase#toString(org.einnovator.util.model.ToStringCreator)
	 */
	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator
				.append("type", type)
				.append("types", types)
				.append("orgs", orgs)
				.append("ops", ops)
				.append("tree", tree)
				);
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
		result = prime * result + ((tree == null) ? 0 : tree.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((types == null) ? 0 : types.hashCode());
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
		GroupOptions other = (GroupOptions) obj;
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
		if (tree == null) {
			if (other.tree != null)
				return false;
		} else if (!tree.equals(other.tree))
			return false;
		if (type != other.type)
			return false;
		if (types == null) {
			if (other.types != null)
				return false;
		} else if (!types.equals(other.types))
			return false;
		return true;
	}


	
}
