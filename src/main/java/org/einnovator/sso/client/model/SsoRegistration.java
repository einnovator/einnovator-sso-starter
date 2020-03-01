package org.einnovator.sso.client.model;

import java.util.List;
import java.util.Map;

import org.einnovator.util.model.Application;
import org.einnovator.util.model.ObjectBase;
import org.einnovator.util.model.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Descriptor with details for application registration in the SSO Gateway at startup.
 * 
 * <p>If property {@link #auto} is true, registration is done automatically.
 * <p>Property {@link #roles} specifies the set of roles to register.
 * <p>Example configuration in {@code application.yaml}:
 * <p>sso:
 * <p>  server : https://sso.mydomain.com
 * <p>  client-id: myapp
 * <p>  client-secret: myapp$secret
 * <p>  registration:
 * <p>    auto: true
 * <p>    roles:
 * <p>    - name: "CUSTOMER_MANAGER"
 * <p>      display-name: "Customer Manager"
 * <p>      type: GLOBAL
 * <p>      description: "User with this role can manage Customers" 
 * 
 * @author support@einnovator.org
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class SsoRegistration extends ObjectBase {

	@JsonIgnore
	private boolean auto;
	
	private Application application;
	
	private List<Role> roles;
	
	private Map<String, Object> properties;

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

	/**
	 * Get the value of property {@code properties}.
	 *
	 * @return the properties
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}


	/**
	 * Set the value of property {@code properties}.
	 *
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}


	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
			.append("auto", auto)
			.append("application", application)
			.append("roles", roles)
			.append("properties", properties)
			;
	}

	
}
