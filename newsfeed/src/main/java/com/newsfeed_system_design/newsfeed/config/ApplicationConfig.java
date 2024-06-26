package com.newsfeed_system_design.newsfeed.config;

import com.newsfeed_system_design.newsfeed.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfig {
    private final UserRepository userRepository;

    @Autowired
    public ApplicationConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // reference: https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt
    // https://stackoverflow.com/questions/55548290/using-bcrypt-in-spring
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // user details service interface used by spring security, retrieve user authentication and authorization info
    // UserDetails object related to authentication
    // returns UserDetails object
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }

    // reference: https://medium.com/@iamssrofficial/demystifying-spring-security-and-its-architecture-25e537e4d53b
    // authentication manager and provider are used for spring authentication
    // authentication manager has list of authentication providers

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // reference: https://www.geeksforgeeks.org/spring-security-authentication-providers/
    // authentication provider, class to implement authentication
    // DaoAuthenticationProvider - authentication provider used in spring security to authenticate users stored in database
    // retrieves user credentials from database, compares them to credentials provided by user login
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        // DaoAuthenticationProvider uses UserDetailsService to retrieve user information from the database during authentication.
        daoAuthProvider.setUserDetailsService(userDetailsService());
        daoAuthProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthProvider;
    }
}
