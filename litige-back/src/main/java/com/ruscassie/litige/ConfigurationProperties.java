package com.ruscassie.litige;

import org.springframework.web.cors.CorsConfiguration;

@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "configuration", ignoreUnknownFields = false)
public class ConfigurationProperties {
	
	private final CorsConfiguration cors = new CorsConfiguration();


	public CorsConfiguration getCors() {
		return cors;
	}

}
