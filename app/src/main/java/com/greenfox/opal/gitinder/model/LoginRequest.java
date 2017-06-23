package com.greenfox.opal.gitinder.model;

public class LoginRequest {
    String username;
    String accessToken;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.accessToken = password;
    }

    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
