package com.ruscassie.litige;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class AuthorizationLitigeApplication {

	public static void main(final String[] args) {
		SpringApplication.run(AuthorizationLitigeApplication.class, args);
	}

}