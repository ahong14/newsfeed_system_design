package com.newsfeed_system_design.graph_service.controllers;

import com.newsfeed_system_design.graph_service.models.User;
import com.newsfeed_system_design.graph_service.services.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/newsfeed/v1/follow")
public class UserFollowingController {
    private final UserFollowingService userFollowingService;

    @Autowired
    public UserFollowingController(UserFollowingService userFollowingService) {
        this.userFollowingService = userFollowingService;
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        return userFollowingService.addUser(user);
    }
}
