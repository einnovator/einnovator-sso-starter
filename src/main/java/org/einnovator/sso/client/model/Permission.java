package org.einnovator.sso.client.model;

import java.util.List;

import org.einnovator.util.model.EntityBase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Permission extends EntityBase {	

		
	private String key;

	private String name;
	
	private String app;

	private String description;

	private String category;
	
	private String imgUri;

	private String icon;
	
	public Permission() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}

	public String getImgUri() {
		return imgUri;
	}

	public void setImgUri(String imgUri) {
		this.imgUri = imgUri;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public static Permission find(String id, List<Permission> perms) {
		if (perms!=null) {
			for (Permission perm: perms) {
				if (id.equalsIgnoreCase(perm.getKey())) {
					return perm;
				}
			}
		}
		return null;
	}
	

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" 
				+ (id != null ? "id=" + id + ", " : "")
				+ (key != null ? "key=" + key + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (category != null ? "category=" + category : "")
				+ (icon != null ? "icon=" + icon + ", " : "")
				+ (imgUri != null ? "imgUri=" + imgUri + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (app != null ? "app=" + app + ", " : "")
				+ "]";
	}

}
