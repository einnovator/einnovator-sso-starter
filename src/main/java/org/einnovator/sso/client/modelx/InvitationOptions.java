package org.einnovator.sso.client.modelx;

import org.einnovator.util.model.EntityOptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvitationOptions extends EntityOptions {

	public InvitationOptions() {
	}

	
}
