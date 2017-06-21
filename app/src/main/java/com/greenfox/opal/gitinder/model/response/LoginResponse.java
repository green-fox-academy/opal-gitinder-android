package com.greenfox.opal.gitinder.model.response;

public class LoginResponse extends BaseResponse {
    String token;

    public LoginResponse(String username, String token) {
        this.status = "ok";
        this.token = token;
    }

    public LoginResponse(String message) {
        super("error", message);
    }

    public String getToken() {
        return token;
    }
}
