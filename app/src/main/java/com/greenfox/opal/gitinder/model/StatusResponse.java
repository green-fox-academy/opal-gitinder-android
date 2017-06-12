package com.greenfox.opal.gitinder.model;

public class StatusResponse {
    String status;
    String token;
    String message;

    public StatusResponse(String status, String value) {
        this.status = status;
        if (status.equals("ok")) {
            this.token = value;
        } else {
            this.message = value;
        }
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
