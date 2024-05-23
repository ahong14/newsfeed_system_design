package com.newsfeed_system_design.newsfeed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final JwtAuthFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    // setup spring security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // setup security filter chain

        // disable CSRF
        http.csrf(csrf -> {
            csrf.disable();
        });

        // allow unauthenticated requests to /api/newsfeed/v1/auth/** URIs
        // everything else must be authenticated
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/api/newsfeed/v1/auth/**").permitAll();
            auth.anyRequest().authenticated();
        });

        // stateless request, every request treated as new
        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        // set authentication provider
        // setup jwt filter to check for auth token in header before
        http.authenticationProvider(this.authenticationProvider).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}