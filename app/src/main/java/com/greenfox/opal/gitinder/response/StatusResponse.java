package com.greenfox.opal.gitinder.response;

public class StatusResponse {
    String status;
    String token;
    String message;

    public StatusResponse() {
    }

    public String getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}
