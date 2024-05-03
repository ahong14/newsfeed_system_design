package com.newsfeed_system_design.newsfeed.services;

import com.newsfeed_system_design.newsfeed.models.User;
import com.newsfeed_system_design.newsfeed.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     *
     * @param newUser - new user record to be created
     * @return - created user
     */
    @Override
    public User createUser(User newUser) {
        User foundUser = this.userRepository.findUserByEmail(newUser.getEmail());
        if (foundUser != null) {
            throw new IllegalArgumentException("User found for email");
        }
        return this.userRepository.save(newUser);
    }

    /**
     *
     * @param userId - user id to find user by
     * @return - return found user
     */
    @Override
    public User getUser(UUID userId) {
        return this.userRepository.findById(userId).orElseThrow();
    }
}