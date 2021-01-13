package org.einnovator.sso.client.modelx;

import org.einnovator.sso.client.model.Role;
import org.einnovator.util.model.EntityOptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Options for {@code Role} lookup and operations.
 * 
 * @see RoleFilter
 * @see org.einnovator.sso.client.manager.RoleManager
 * @see org.einnovator.sso.client.model.Role
 * @author support@einnovator.org
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleOptions extends EntityOptions<Role> {

	public RoleOptions() {
	}

	
}
