package org.einnovator.sso.client.modelx;

import org.einnovator.sso.client.model.Client;
import org.einnovator.util.model.EntityOptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientOptions extends EntityOptions<Client> {

	public static final ClientOptions DEFAULT = new ClientOptions();

	public ClientOptions() {
	}

	
}
