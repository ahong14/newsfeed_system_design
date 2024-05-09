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

	// reference: https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt
	// https://stackoverflow.com/questions/55548290/using-bcrypt-in-spring
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
