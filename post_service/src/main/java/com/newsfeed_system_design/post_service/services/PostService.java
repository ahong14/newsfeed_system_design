package com.newsfeed_system_design.post_service.services;

import com.newsfeed_system_design.post_service.models.CreatePostRequest;
import com.newsfeed_system_design.post_service.models.Post;

import java.util.UUID;

public interface PostService {
    Post createPost(CreatePostRequest createPostRequest);

    Post getUserPost(UUID userId, UUID postId);

    Post getPostsForUser(UUID userId);

    void deleteUserPost(UUID userId, UUID postId);

    Post updatePostContent(UUID userId, UUID postId, String newContent);
}
