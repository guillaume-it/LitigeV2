package com.ruscassie.litige;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class AuthorizationLitigeApplication {

	public static void main(final String[] args) {
		SpringApplication.run(AuthorizationLitigeApplication.class, args);
	}

}
