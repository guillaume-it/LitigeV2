package com.ruscassie.litige;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
class Resource1ServerApplication extends SpringBootServletInitializer {

	public static void main(final String[] args) {
		SpringApplication.run(Resource1ServerApplication.class, args);
	}

}