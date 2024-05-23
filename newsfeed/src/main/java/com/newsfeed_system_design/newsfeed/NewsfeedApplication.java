package com.newsfeed_system_design.newsfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class NewsfeedApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsfeedApplication.class, args);
	}
}
