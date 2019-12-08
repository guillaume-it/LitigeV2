package com.ruscassie.litige.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ResourceServerApplication extends SpringBootServletInitializer {

	public static void main(final String[] args) {
		SpringApplication.run(ResourceServerApplication.class, args);
	}

}