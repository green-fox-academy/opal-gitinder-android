package com.greenfox.opal.gitinder.service;

import com.greenfox.opal.gitinder.ApiService;
import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.response.ErrorResponse;
import com.greenfox.opal.gitinder.response.LoginResponse;
import com.greenfox.opal.gitinder.response.StatusResponse;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class MockServer implements ApiService {
    @Override
    public MockCall login(@Body final LoginRequest loginRequest) {
        return new MockCall() {
            @Override
            public void enqueue(Callback callback) {
                StatusResponse response;
                if (loginRequest.getUsername().isEmpty() || loginRequest.getAccessToken().isEmpty()) {
                    String message = "Missing parameter(s):";
                    if (loginRequest.getUsername().isEmpty()) {
                        message += " username";
                    }
                    if (loginRequest.getAccessToken().isEmpty()) {
                        message += " accessToken";
                    }
                    message += "!";
                    response = new ErrorResponse("error", message);
                } else {
                    response = new LoginResponse("ok", "abc123");
                }
                callback.onResponse(null, Response.success(response));
            }
        };
    }
}
