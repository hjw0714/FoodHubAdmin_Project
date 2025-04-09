package com.application.foodhubAdmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FoodhubAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodhubAdminApplication.class, args);
	}

}
