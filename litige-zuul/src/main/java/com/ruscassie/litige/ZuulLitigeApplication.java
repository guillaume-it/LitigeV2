package com.ruscassie.litige;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ZuulLitigeApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ZuulLitigeApplication.class, args);
	}

}
