package com.newsfeed_system_design.newsfeed.services;

import com.newsfeed_system_design.newsfeed.models.User;

import java.util.UUID;

public interface UserService {
    User createUser(User newUser);

    User getUser(UUID userId);
}
