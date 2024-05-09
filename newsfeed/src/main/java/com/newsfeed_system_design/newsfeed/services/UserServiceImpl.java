package com.newsfeed_system_design.newsfeed.services;

import com.newsfeed_system_design.newsfeed.models.User;
import com.newsfeed_system_design.newsfeed.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
        // hash password
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return this.userRepository.save(newUser);
    }

    /**
     *
     * @param userId - user id to find user by
     * @return - return found user
     */
    @Override
    public User getUser(UUID userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found for ID: " + userId));
    }
}