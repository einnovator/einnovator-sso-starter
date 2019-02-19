package org.einnovator.sso.client.config;

import java.util.List;

import org.einnovator.util.model.ObjectBase;
import org.einnovator.util.model.ToStringCreator;

public class AccessConfiguration extends ObjectBase {
	
	public static final String ROLE_CLIENT = "ROLE_CLIENT";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";

	private List<String> admin = null;
	
	public AccessConfiguration() {
	}
		
	public List<String> getAdmin() {
		return admin;
	}

	public void setAdmin(List<String> admin) {
		this.admin = admin;
	}

	@Override
	public ToStringCreator toString(ToStringCreator creator) {
		return super.toString(creator)
				.append("admin", admin);
	}


	public boolean isAdmin(String name) {
		if (admin!=null) {
			for (String user: admin) {
				if (user.trim().equals(name.trim())) {
					return true;
				}
			}			
		}
		return false;
	}
	
	

}
