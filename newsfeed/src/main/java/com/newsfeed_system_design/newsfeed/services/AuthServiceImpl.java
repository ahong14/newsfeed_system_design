package com.newsfeed_system_design.newsfeed.services;

import com.newsfeed_system_design.newsfeed.models.User;
import com.newsfeed_system_design.newsfeed.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;


// reference: https://medium.com/@tericcabrel/implement-jwt-authentication-in-a-spring-boot-3-application-5839e4fd8fac
@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;

    private final JwtServiceImpl jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, JwtServiceImpl jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     *
     * @param email - email of user
     * @param password - password of user
     * @return - generated JWT token if login credentials correct
     */
    @Override
    public String loginUser(String email, String password) {
        // use authentication manager, authenticate method calls provider
        // if unable to authenticate, throws BadCredentialsException
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                email,
                password)
        );

        Optional<User> foundUser = this.userRepository.findUserByEmail(email);
        if (foundUser.isEmpty()) {
            throw new NoSuchElementException("User not found with email.");
        }

        User foundUserObject = foundUser.get();

        return jwtService.generateToken(foundUserObject);
    }
}
