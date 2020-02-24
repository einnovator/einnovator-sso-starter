package org.einnovator.sso.client.model;

import java.util.List;

import org.einnovator.util.model.EntityBase;
import org.einnovator.util.model.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Permission extends EntityBase {	
		
	private String key;

	private String name;
	
	private String app;

	private String description;

	private String category;
	
	private String img;

	private String icon;
	
	/**
	 * Create instance of {@code Permission}.
	 *
	 */
	public Permission() {
	}
	
	
	/**
	 * Get the value of property {@code key}.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}


	/**
	 * Set the value of property {@code key}.
	 *
	 * @param key the value of property key
	 */
	public void setKey(String key) {
		this.key = key;
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
	 * @param name the value of property name
	 */
	public void setName(String name) {
		this.name = name;
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


	/**
	 * Get the value of property {@code description}.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * Set the value of property {@code description}.
	 *
	 * @param description the value of property description
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * Get the value of property {@code img}.
	 *
	 * @return the img
	 */
	public String getImg() {
		return img;
	}


	/**
	 * Set the value of property {@code img}.
	 *
	 * @param img the value of property img
	 */
	public void setImg(String img) {
		this.img = img;
	}


	/**
	 * Get the value of property {@code icon}.
	 *
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}


	/**
	 * Set the value of property {@code icon}.
	 *
	 * @param icon the value of property icon
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}


	/**
	 * Set the value of property {@code key}.
	 *
	 * @param key the value of property key
	 * @return this {@code Permission}
	 */
	public Permission withKey(String key) {
		this.key = key;
		return this;
	}


	/**
	 * Set the value of property {@code name}.
	 *
	 * @param name the value of property name
	 * @return this {@code Permission}
	 */
	public Permission withName(String name) {
		this.name = name;
		return this;
	}


	/**
	 * Set the value of property {@code app}.
	 *
	 * @param app the value of property app
	 * @return this {@code Permission}
	 */
	public Permission withApp(String app) {
		this.app = app;
		return this;
	}


	/**
	 * Set the value of property {@code description}.
	 *
	 * @param description the value of property description
	 * @return this {@code Permission}
	 */
	public Permission withDescription(String description) {
		this.description = description;
		return this;
	}


	/**
	 * Set the value of property {@code category}.
	 *
	 * @param category the value of property category
	 * @return this {@code Permission}
	 */
	public Permission withCategory(String category) {
		this.category = category;
		return this;
	}


	/**
	 * Set the value of property {@code img}.
	 *
	 * @param img the value of property img
	 * @return this {@code Permission}
	 */
	public Permission withImg(String img) {
		this.img = img;
		return this;
	}


	/**
	 * Set the value of property {@code icon}.
	 *
	 * @param icon the value of property icon
	 * @return this {@code Permission}
	 */
	public Permission withIcon(String icon) {
		this.icon = icon;
		return this;
	}


	public static Permission find(String id, List<Permission> perms) {
		if (perms!=null) {
			for (Permission perm: perms) {
				if (id.equalsIgnoreCase(perm.key)) {
					return perm;
				}
			}
		}
		return null;
	}
	

	
	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
			.append("key", key)
			.append("name", name)
			.append("category", category)
			.append("icon", icon)
			.append("img", img)
			.append("app", app)
			.append("description", description)
			;

	}

}
