package com.newsfeed_system_design.newsfeed.controllers;

import com.newsfeed_system_design.newsfeed.models.CreateUserRequest;
import com.newsfeed_system_design.newsfeed.models.User;
import com.newsfeed_system_design.newsfeed.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.UUID;

@RestController
@RequestMapping(path ="/api/newsfeed/user")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     *
     * @param createUserRequest create user request containing first/last name, email and dob
     * @return created user
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        LocalDateTime userCreatedAtDateTime = LocalDateTime.now();
        String userStringDob = createUserRequest.getDateOfBirth();
        System.out.println(userStringDob);
        LocalDate userDob = LocalDate.parse(userStringDob, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        System.out.println(userDob);
        User createNewUser = new User(
                UUID.randomUUID(),
                createUserRequest.getFirstName(),
                createUserRequest.getLastName(),
                createUserRequest.getEmail(),
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
    @GetMapping(path = "{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User foundUser = this.userService.getUser(UUID.fromString(userId));
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }
}