package com.newsfeed_system_design.newsfeed.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class LoginUserRequest {
    @Email
    private String email;

    @Size(min = 8)
    private String password;

    public LoginUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
