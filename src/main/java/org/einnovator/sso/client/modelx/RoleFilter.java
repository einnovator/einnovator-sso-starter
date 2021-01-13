package org.einnovator.sso.client.modelx;

import org.einnovator.sso.client.model.RoleType;
import org.einnovator.util.model.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A filter for {@code Role}s.
 * 
 * @see org.einnovator.sso.client.manager.RoleManager
 * @see org.einnovator.sso.client.model.Role
 * @author support@einnovator.org
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleFilter extends RoleOptions {

	private String q;
	
	private String name;

	private RoleType type;
	
	private String group;
		
	private Boolean manage;

	private String category;

	private String app;

	/**
	 * Create instance of {@code RoleFilter}.
	 *
	 */
	public RoleFilter() {
	}
	
	/**
	 * Get the value of property {@code q}.
	 *
	 * @return the q
	 */
	public String getQ() {
		return q;
	}

	/**
	 * Set the value of property {@code q}.
	 *
	 * @param q the q
	 */
	public void setQ(String q) {
		this.q = q;
	}


	/**
	 * Get the value of property {@code name}.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the value of property {@code name}.
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Get the value of property {@code type}.
	 *
	 * @return the type
	 */
	public RoleType getType() {
		return type;
	}


	/**
	 * Set the value of property {@code type}.
	 *
	 * @param type the type
	 */
	public void setType(RoleType type) {
		this.type = type;
	}

	/**
	 * Get the value of property {@code group}.
	 *
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * Set the value of property {@code group}.
	 *
	 * @param group the group
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Get the value of property {@code manage}.
	 *
	 * @return the manage
	 */
	public Boolean getManage() {
		return manage;
	}

	/**
	 * Set the value of property {@code manage}.
	 *
	 * @param manage the value of property manage
	 */
	public void setManage(Boolean manage) {
		this.manage = manage;
	}

	/**
	 * Get the value of property {@code category}.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Set the value of property {@code category}.
	 *
	 * @param category the value of property category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Get the value of property {@code app}.
	 *
	 * @return the app
	 */
	public String getApp() {
		return app;
	}

	/**
	 * Set the value of property {@code app}.
	 *
	 * @param app the value of property app
	 */
	public void setApp(String app) {
		this.app = app;
	}
	

	//
	// With
	//


	/**
	 * Set the value of property {@code q}.
	 *
	 * @param q the q to with
	 * @return this
	 */
	public RoleFilter withQ(String q) {
		this.q = q;
		return this;
	}

	/**
	 * Set the value of property {@code name}.
	 *
	 * @param name the name to with
	 * @return this
	 */
	public RoleFilter withName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Set the value of property {@code group}.
	 *
	 * @param group the group to with
	 * @return this
	 */
	public RoleFilter withGroup(String group) {
		this.group = group;
		return this;
	}


	/**
	 * Set the value of property {@code type}.
	 *
	 * @param type the type to with
	 * @return this
	 */
	public RoleFilter withType(RoleType type) {
		this.type = type;
		return this;
	}

	/**
	 * Set the value of property {@code app}.
	 *
	 * @param app the value of property app
	 * @return this {@code RoleFilter}
	 */
	public RoleFilter withApp(String app) {
		this.app = app;
		return this;
	}

	/**
	 * Set the value of property {@code category}.
	 *
	 * @param category the value of property category
	 * @return this
	 */
	public RoleFilter withCategory(String category) {
		this.category = category;
		return this;
	}

	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
				.append("q", q)
				.append("name", name)
				.append("type", type)
				.append("group", group)
				.append("manage", manage)
				.append("category", category)
				.append("app", app)
			;
	}
	
	
	
}
