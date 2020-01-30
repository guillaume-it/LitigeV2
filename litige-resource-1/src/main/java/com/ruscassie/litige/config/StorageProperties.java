package com.ruscassie.litige.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage")
public class StorageProperties {

	private String location;

	public String getLocation() {
		return location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

}