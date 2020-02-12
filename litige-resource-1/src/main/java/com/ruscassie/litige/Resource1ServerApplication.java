package com.ruscassie.litige;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.ruscassie.litige.config.StorageProperties;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableEurekaClient
@EnableConfigurationProperties({StorageProperties.class,})
public class Resource1ServerApplication extends SpringBootServletInitializer {

	public static void main(final String[] args) {
		SpringApplication.run(Resource1ServerApplication.class, args);
	}

}