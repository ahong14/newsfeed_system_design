package com.newsfeed_system_design.newsfeed.services;

import com.newsfeed_system_design.newsfeed.models.User;

import java.util.Date;

public interface AuthService {

    String loginUser(String email, String password);

}
