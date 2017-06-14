package com.greenfox.opal.gitinder.response;

public class ErrorResponse extends StatusResponse {
    public ErrorResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
