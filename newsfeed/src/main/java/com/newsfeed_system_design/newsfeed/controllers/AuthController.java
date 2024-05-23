package com.newsfeed_system_design.newsfeed.controllers;

import com.newsfeed_system_design.newsfeed.models.CreateUserRequest;
import com.newsfeed_system_design.newsfeed.models.LoginResponse;
import com.newsfeed_system_design.newsfeed.models.LoginUserRequest;
import com.newsfeed_system_design.newsfeed.models.User;
import com.newsfeed_system_design.newsfeed.services.AuthServiceImpl;
import com.newsfeed_system_design.newsfeed.services.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController()
@RequestMapping("/api/newsfeed/v1/auth")
public class AuthController {
    private final AuthServiceImpl authService;

    private final UserServiceImpl userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthServiceImpl authService, UserServiceImpl userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signupUser(@RequestBody CreateUserRequest createUserRequest) {
        LocalDateTime userCreatedAtDateTime = LocalDateTime.now();
        String userStringDob = createUserRequest.getDateOfBirth();
        LocalDate userDob = LocalDate.parse(userStringDob, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        User createNewUser = new User(
                UUID.randomUUID(),
                createUserRequest.getFirstName(),
                createUserRequest.getLastName(),
                createUserRequest.getEmail(),
                createUserRequest.getPassword(),
                userDob,
                userCreatedAtDateTime
        );
        User createdUser = this.userService.createUser(createNewUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginUserRequest loginUserRequest) {
        String authToken = this.authService.loginUser(loginUserRequest.getEmail(), loginUserRequest.getPassword());
        LoginResponse loginResponse = new LoginResponse(authToken, LoginResponse.TOKEN_EXPIRES_IN);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", "Bearer " + authToken);
        return new ResponseEntity<>(loginResponse, responseHeaders, HttpStatus.OK);
    }
}
