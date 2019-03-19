package org.einnovator.sso.client.modelx;

import org.einnovator.util.model.Address;
import org.einnovator.util.model.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupFilter extends GroupOptions {

	private String q;
	
	private String user;

	private String owner;

	private String root;

	private String parent;

	private Address address;

	private Boolean qaddress;
	
	private Boolean featured;
	
	private Boolean hidden;


	public GroupFilter() {
	}


	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}


	/**
	 * Get the value of property {@code root}.
	 *
	 * @return the root
	 */
	public String getRoot() {
		return root;
	}

	/**
	 * Set the value of property {@code root}.
	 *
	 * @param root the root to set
	 */
	public void setRoot(String root) {
		this.root = root;
	}

	/**
	 * Get the value of property {@code parent}.
	 *
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * Set the value of property {@code parent}.
	 *
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * Get the value of property {@code address}.
	 *
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Set the value of property {@code address}.
	 *
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}


	/**
	 * Get the value of property {@code qaddress}.
	 *
	 * @return the qaddress
	 */
	public Boolean getQaddress() {
		return qaddress;
	}

	/**
	 * Set the value of property {@code qaddress}.
	 *
	 * @param qaddress the qaddress to set
	 */
	public void setQaddress(Boolean qaddress) {
		this.qaddress = qaddress;
	}

	/**
	 * Get the value of property {@code featured}.
	 *
	 * @return the featured
	 */
	public Boolean getFeatured() {
		return featured;
	}

	/**
	 * Set the value of property {@code featured}.
	 *
	 * @param featured the featured to set
	 */
	public void setFeatured(Boolean featured) {
		this.featured = featured;
	}

	/**
	 * Get the value of property {@code hidden}.
	 *
	 * @return the hidden
	 */
	public Boolean getHidden() {
		return hidden;
	}

	/**
	 * Set the value of property {@code hidden}.
	 *
	 * @param hidden the hidden to set
	 */
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 * Get the value of property {@code user}.
	 *
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Set the value of property {@code user}.
	 *
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	

	/**
	 * Get the value of property {@code owner}.
	 *
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}


	/**
	 * Set the value of property {@code owner}.
	 *
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}


	/* (non-Javadoc)
	 * @see org.einnovator.util.model.ObjectBase#toString(org.einnovator.util.model.ToStringCreator)
	 */
	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator
				.append("q", q)
				.append("root", root)
				.append("parent", parent)
				.append("user", user)
				.append("owner", owner)
				);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((featured == null) ? 0 : featured.hashCode());
		result = prime * result + ((hidden == null) ? 0 : hidden.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((q == null) ? 0 : q.hashCode());
		result = prime * result + ((qaddress == null) ? 0 : qaddress.hashCode());
		result = prime * result + ((root == null) ? 0 : root.hashCode());
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
		GroupFilter other = (GroupFilter) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (featured == null) {
			if (other.featured != null)
				return false;
		} else if (!featured.equals(other.featured))
			return false;
		if (hidden == null) {
			if (other.hidden != null)
				return false;
		} else if (!hidden.equals(other.hidden))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (q == null) {
			if (other.q != null)
				return false;
		} else if (!q.equals(other.q))
			return false;
		if (qaddress == null) {
			if (other.qaddress != null)
				return false;
		} else if (!qaddress.equals(other.qaddress))
			return false;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		return true;
	}

	
}
