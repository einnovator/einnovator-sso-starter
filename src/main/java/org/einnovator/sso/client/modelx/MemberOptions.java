package org.einnovator.sso.client.modelx;

import org.einnovator.sso.client.model.User;
import org.einnovator.util.model.EntityOptions;
import org.einnovator.util.model.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Options for {@code Member} lookup and operations.
 * 
 * @see MemberFilter
 * @see org.einnovator.sso.client.model.Member
 * @author support@einnovator.org
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberOptions extends EntityOptions<User> {

	
	public MemberOptions() {
	}


	@Override
	public ToStringCreator toString(ToStringCreator creator) {
		return super.toString(creator
				);
	}
	

}
