package com.newsfeed_system_design.post_service.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class CreatePostRequest {

    @NotEmpty
    private UUID posterUserId;


    @Size(min = 2, max = 256)
    private String content;

    public UUID getPosterUserId() {
        return posterUserId;
    }

    public void setPosterUserId(UUID posterUserId) {
        this.posterUserId = posterUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
