package com.greenfox.opal.gitinder.model.response;

public class LoginResponse extends BaseResponse {
    String token;

    public LoginResponse() {
    }
  
    public LoginResponse(String status, String token) {
        this.status = status;
        this.token = token;
    }

    public LoginResponse(String message) {
        super("error", message);
    }

    public String getToken() {
        return token;
    }
}
