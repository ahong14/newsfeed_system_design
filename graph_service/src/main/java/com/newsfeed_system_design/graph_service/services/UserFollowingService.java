package com.newsfeed_system_design.graph_service.services;


import com.newsfeed_system_design.graph_service.models.User;
import com.newsfeed_system_design.graph_service.repositories.UserFollowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFollowingService {
    private final UserFollowingRepository userFollowingRepository;

    @Autowired
    public UserFollowingService(UserFollowingRepository userFollowingRepository) {
        this.userFollowingRepository = userFollowingRepository;
    }

    public User addUser(User newUser) {
        return userFollowingRepository.save(newUser);
    }
}
