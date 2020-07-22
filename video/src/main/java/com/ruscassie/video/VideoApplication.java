package com.ruscassie.video;

import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class VideoApplication {

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(VideoApplication.class, args)));
	}
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new ResourcelessTransactionManager();
	}
}
