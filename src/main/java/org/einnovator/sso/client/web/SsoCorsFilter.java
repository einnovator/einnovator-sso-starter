package org.einnovator.sso.client.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/**
 * A CORS filter.
 *
 * @author support@einnovator.org
 *
 */
public class SsoCorsFilter extends CorsFilter {

	@Autowired
	public SsoCorsFilter(CorsConfigurationSource configSource) {
		super(configSource);
	}
	

}
