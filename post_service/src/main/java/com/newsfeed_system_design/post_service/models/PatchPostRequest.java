package com.newsfeed_system_design.post_service.models;

public class PatchPostRequest {
    private String newContent;

    public PatchPostRequest(String newContent) {
        this.newContent = newContent;
    }

    public PatchPostRequest() {
    }

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }
}
