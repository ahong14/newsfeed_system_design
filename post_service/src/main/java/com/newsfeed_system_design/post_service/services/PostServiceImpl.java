package com.newsfeed_system_design.post_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newsfeed_system_design.post_service.models.CreatePostRequest;
import com.newsfeed_system_design.post_service.models.Post;
import com.newsfeed_system_design.post_service.models.PostActionEnums;
import com.newsfeed_system_design.post_service.models.PostMessage;
import com.newsfeed_system_design.post_service.repositories.PostRepository;
import com.newsfeed_system_design.post_service.utils.ObjectJsonWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private KafkaTemplate<String, String> kafkaTemplate;



    @Autowired
    public PostServiceImpl(PostRepository postRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.postRepository = postRepository;
        this.kafkaTemplate = kafkaTemplate;
    }


    /**
     *
     * @param createPostRequest - post request with content and user id
     * @return - created post
     */
    @Override
    public Post createPost(CreatePostRequest createPostRequest) throws JsonProcessingException {
        Post createNewPost = new Post();
        UUID newPostId = UUID.randomUUID();
        Date newPostCreatedDate = new Date();
        createNewPost.setId(newPostId);
        createNewPost.setPostUserId(createPostRequest.getPosterUserId());
        createNewPost.setContent(createPostRequest.getContent());
        createNewPost.setCreatedDate(newPostCreatedDate);
        createNewPost.setUpdatedDate(newPostCreatedDate);
        // save post to db
        this.postRepository.save(createNewPost);
        // publish message to kafka topic
        PostMessage postMessage = new PostMessage(createPostRequest.getPosterUserId(), newPostId, PostActionEnums.CREATED, new Date());
        String postMessageJson = ObjectJsonWriter.writeObjectToString(postMessage);
        this.kafkaTemplate.send("post_updates", postMessageJson);
        return createNewPost;
    }


    /**
     *
     * @param userId - user id of post
     * @param postId - post id
     * @return - found post for post id and user id
     */
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

    /**
     *
     * @param userId - user id of post
     * @param postId - post id to be deleted
     */
    @Override
    public void deleteUserPost(UUID userId, UUID postId) {
        this.postRepository.deleteByPostUserIdAndId(userId, postId);
    }

    /**
     *
     * @param userId - user id of post
     * @param postId - post id
     * @param newContent - new content of post to be updated to
     * @return - updated post
     */
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
