package com.greenfox.opal.gitinder.response;

public class BaseResponse {
    String status;
    String message;

    public BaseResponse() {
    }

    public BaseResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
}
