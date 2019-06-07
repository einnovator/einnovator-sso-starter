package org.einnovator.sso.client.model;

import java.util.List;

import org.einnovator.util.model.Application;
import org.einnovator.util.model.ObjectBase;
import org.einnovator.util.model.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class SsoRegistration extends ObjectBase {

	@JsonIgnore
	private boolean auto;
	
	private Application application;
	
	private List<Role> roles;

	public SsoRegistration() {
	}
	
	
	/**
	 * Get the value of property {@code auto}.
	 *
	 * @return the auto
	 */
	public boolean isAuto() {
		return auto;
	}


	/**
	 * Set the value of property {@code auto}.
	 *
	 * @param auto the auto to set
	 */
	public void setAuto(boolean auto) {
		this.auto = auto;
	}


	/**
	 * Get the value of property {@code application}.
	 *
	 * @return the application
	 */
	public Application getApplication() {
		return application;
	}


	/**
	 * Set the value of property {@code application}.
	 *
	 * @param application the application to set
	 */
	public void setApplication(Application application) {
		this.application = application;
	}


	/**
	 * Get the value of property {@code roles}.
	 *
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}


	/**
	 * Set the value of property {@code roles}.
	 *
	 * @param roles the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
			.append("auto", auto)
			.append("application", application)
			.append("roles", roles)
			;
	}

	
}
