package com.newsfeed_system_design.newsfeed.config;

import com.newsfeed_system_design.newsfeed.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


// reference: https://medium.com/@tericcabrel/implement-jwt-authentication-in-a-spring-boot-3-application-5839e4fd8fac
// middleware to validate JWT in Authorization header
// OncePerRequestFilter - executes per request
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService, HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       // check if header contains Authorization Bearer {token}
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // call next filter in filter chain
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // extract token from header, decode token to get email
            final String jwt = authHeader.split(" ")[1];
            final String email = jwtService.extractEmail(jwt);

            // get current authentication info from security context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // if email found and not authenticated
            if (email != null && authentication == null) {
                // load user details for email extracted from token
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

                // check if jwt token is valid, not expired and user details matches email
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // create Authentication in spring security context
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    // build details object from HttpServletRequest
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // update security context, set authentication
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // call next filter in filter chain
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
