package com.newsfeed_system_design.newsfeed.controllers;

import com.newsfeed_system_design.newsfeed.models.CreateUserRequest;
import com.newsfeed_system_design.newsfeed.models.User;
import com.newsfeed_system_design.newsfeed.services.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
public class UserController {
    private final UserServiceImpl userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     *
     * @param createUserRequest create user request containing first/last name, email and dob
     * @return created user
     */
    @PostMapping("/api/newsfeed/v1/user")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        LocalDateTime userCreatedAtDateTime = LocalDateTime.now();
        String userStringDob = createUserRequest.getDateOfBirth();
        logger.info("user string dob: {}", userStringDob);
        LocalDate userDob = LocalDate.parse(userStringDob, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        logger.info("parsed LocalDate dob: {}", userDob);
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

    /**
     *
     * @param userId - id of user to retrieve
     * @return - found user
     */
    @GetMapping(path = "/api/newsfeed/v1/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User foundUser = this.userService.getUser(UUID.fromString(userId));
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }
}