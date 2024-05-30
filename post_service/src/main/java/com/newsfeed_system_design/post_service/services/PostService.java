package com.newsfeed_system_design.post_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newsfeed_system_design.post_service.models.CreatePostRequest;
import com.newsfeed_system_design.post_service.models.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post createPost(CreatePostRequest createPostRequest) throws JsonProcessingException;

    Post savePost(Post createNewPost) throws JsonProcessingException;

    Post getUserPost(UUID userId, UUID postId);

    List<Post> getPostsForUser(UUID userId);

    void deleteUserPost(UUID userId, UUID postId);

    Post updatePostContent(UUID userId, UUID postId, String newContent);

    Post getPost(UUID postId);
}
