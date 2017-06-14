package com.greenfox.opal.gitinder.response;

public class LoginResponse extends StatusResponse {
    public LoginResponse(String status, String token) {
        this.status = status;
        this.token = token;
    }
}
