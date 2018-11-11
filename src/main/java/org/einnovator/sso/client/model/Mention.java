package org.einnovator.sso.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.einnovator.util.MappingUtils;
import org.einnovator.util.model.EntityBase;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mention extends EntityBase {
	
	private String type;

	private String name;

	private String key;

	private String ref;

	private String redirectUri;

	private String uri;
	
	private String icon;
	
	private String imgUri;
	

	public Mention() {
	}
		
	public Mention(Mention mention) {
		MappingUtils.updateObjectFrom(this, mention);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getImgUri() {
		return imgUri;
	}

	public void setImgUri(String imgUri) {
		this.imgUri = imgUri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (uuid != null ? "uuid=" + uuid + ", " : "")
				+ (type != null ? "type=" + type + ", " : "")
				+ (key != null ? "key=" + key + ", " : "")
				+ (ref != null ? "ref=" + ref + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (uri != null ? "uri=" + uri + ", " : "")
				+ (redirectUri != null ? "redirectUri=" + redirectUri + ", " : "")
				+ (imgUri != null ? "imgUri=" + imgUri + ", " : "")
				+ (icon != null ? "icon=" + icon + ", " : "")
				+ (creationDate != null ? "creationDate=" + creationDate : "") 
				+ (lastModified != null ? "lastModified=" + lastModified : "") 
				+ "]";
	}
	
}
