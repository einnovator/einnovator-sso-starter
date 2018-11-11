package org.einnovator.sso.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


public class SsoCorsFilter extends CorsFilter {

	@Autowired
	public SsoCorsFilter(CorsConfigurationSource configSource) {
		super(configSource);
	}
	

}
