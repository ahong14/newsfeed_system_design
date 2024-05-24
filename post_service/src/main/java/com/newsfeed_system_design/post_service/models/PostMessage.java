package com.newsfeed_system_design.post_service.models;

import java.util.Date;
import java.util.UUID;

public class PostMessage {
    private UUID userId;

    private UUID postId;

    private PostActionEnums status;

    private Date timestamp;

    public PostMessage(UUID userId, UUID postId, PostActionEnums status, Date timestamp) {
        this.userId = userId;
        this.postId = postId;
        this.status = status;
        this.timestamp = timestamp;
    }

    public PostMessage() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public PostActionEnums getStatus() {
        return status;
    }

    public void setStatus(PostActionEnums status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
