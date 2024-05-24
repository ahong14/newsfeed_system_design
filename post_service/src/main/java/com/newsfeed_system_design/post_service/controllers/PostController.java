package com.newsfeed_system_design.post_service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newsfeed_system_design.post_service.models.CreatePostRequest;
import com.newsfeed_system_design.post_service.models.PatchPostRequest;
import com.newsfeed_system_design.post_service.models.Post;
import com.newsfeed_system_design.post_service.services.PostServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/newsfeed/v1/posts")
public class PostController {
    private final PostServiceImpl postService;

    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody CreatePostRequest createPostRequest) throws JsonProcessingException {
        Post createdPost = this.postService.createPost(createPostRequest);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{userId}/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable String userId, @PathVariable String postId) {
        Post foundPost = this.postService.getUserPost(UUID.fromString(userId), UUID.fromString(postId));
        return new ResponseEntity<>(foundPost, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{userId}/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String userId, @PathVariable String postId) {
        this.postService.deleteUserPost(UUID.fromString(userId), UUID.fromString(postId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/{userId}/{postId}")
    public ResponseEntity<Post> updatePostContent(@PathVariable String userId, @PathVariable String postId, @RequestBody PatchPostRequest patchPostRequest) {
        Post updatedPost = this.postService.updatePostContent(UUID.fromString(userId), UUID.fromString(postId), patchPostRequest.getNewContent());
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }
}
