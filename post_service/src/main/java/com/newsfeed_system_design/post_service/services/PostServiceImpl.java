package com.newsfeed_system_design.post_service.services;

import com.newsfeed_system_design.post_service.models.CreatePostRequest;
import com.newsfeed_system_design.post_service.models.Post;
import com.newsfeed_system_design.post_service.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(CreatePostRequest createPostRequest) {
        Post createNewPost = new Post();
        UUID newPostId = UUID.randomUUID();
        Date newPostCreatedDate = new Date();
        createNewPost.setId(newPostId);
        createNewPost.setPostUserId(createPostRequest.getPosterUserId());
        createNewPost.setContent(createPostRequest.getContent());
        createNewPost.setCreatedDate(newPostCreatedDate);
        createNewPost.setUpdatedDate(newPostCreatedDate);
        return this.postRepository.save(createNewPost);
    }

    @Override
    public Post getUserPost(UUID userId, UUID postId) {
        Post foundPost = this.postRepository.findByPostUserIdAndId(userId, postId);
        if (foundPost == null) {
            throw new NoSuchElementException("Post ID not found for user");
        }
        return foundPost;
    }

    // TODO
    @Override
    public Post getPostsForUser(UUID userId) {
        return null;
    }

    @Override
    public void deleteUserPost(UUID userId, UUID postId) {
        this.postRepository.deleteByPostUserIdAndId(userId, postId);
    }

    @Override
    public Post updatePostContent(UUID userId, UUID postId, String newContent) {
        Post existingPost = this.postRepository.findByPostUserIdAndId(userId, postId);
        if (existingPost == null) {
            throw new NoSuchElementException("Post ID not found for user");
        }

        // update new content and updated date
        existingPost.setUpdatedDate(new Date());
        existingPost.setContent(newContent);
        return this.postRepository.save(existingPost);
    }
}
