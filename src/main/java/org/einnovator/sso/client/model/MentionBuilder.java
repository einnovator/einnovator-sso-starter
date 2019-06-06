package org.einnovator.sso.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.einnovator.util.MappingUtils;
import org.einnovator.util.model.EntityBase;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MentionBuilder extends EntityBase {
	
	private String type;

	private String name;

	private String key;

	private String ref;

	private String redirectUri;

	private String uri;
	
	private String icon;
	
	private String img;
	

	public MentionBuilder() {
	}
		
	public MentionBuilder(MentionBuilder mention) {
		MappingUtils.updateObjectFrom(this, mention);
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public Mention build() {
		Mention mention = new Mention();
		mention.setType(type);
		mention.setKey(key);
		mention.setName(name);
		mention.setRef(ref);
		mention.setRedirectUri(redirectUri);
		mention.setUri(uri);
		mention.setIcon(icon);
		mention.setImg(img);
		return mention;
	}
}
