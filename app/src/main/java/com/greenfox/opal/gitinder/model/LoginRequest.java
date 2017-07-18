package com.greenfox.opal.gitinder.model;

public class LoginRequest {
    String user_name;
    String access_token;

    public LoginRequest(String username, String password) {
        this.user_name = username;
        this.access_token = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getAccess_token() {
        return access_token;
    }
}
