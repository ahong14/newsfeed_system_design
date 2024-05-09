package com.newsfeed_system_design.newsfeed.exception;

import java.time.LocalDateTime;

public class NewsfeedErrorDetails {
    private LocalDateTime errorTimestamp;
    private String errorMessage;

    public NewsfeedErrorDetails(LocalDateTime errorTimestamp, String errorMessage) {
        this.errorTimestamp = errorTimestamp;
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getErrorTimestamp() {
        return errorTimestamp;
    }

    public void setErrorTimestamp(LocalDateTime errorTimestamp) {
        this.errorTimestamp = errorTimestamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
