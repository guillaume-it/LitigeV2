package com.ruscassie.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class VideoApplication {

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(VideoApplication.class, args)));
	}

}
