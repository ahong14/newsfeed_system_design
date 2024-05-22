package com.newsfeed_system_design.newsfeed.services;

import com.newsfeed_system_design.newsfeed.models.User;

import java.util.Date;

public interface AuthService {
    String generateToken(User user);

    String loginUser(String email, String password);

    Date extractExpiration(String token);
}
