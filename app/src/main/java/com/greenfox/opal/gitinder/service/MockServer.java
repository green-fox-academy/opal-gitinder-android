package com.greenfox.opal.gitinder.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfox.opal.gitinder.ApiService;
import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.response.ErrorResponse;
import com.greenfox.opal.gitinder.response.LoginResponse;
import com.greenfox.opal.gitinder.response.StatusResponse;

import java.io.IOException;

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
                ObjectMapper mapper = new ObjectMapper();
                try {
                    if (loginRequest.getUsername().isEmpty() || loginRequest.getAccessToken().isEmpty()) {
                        String message = "Missing parameter(s):";
                        if (loginRequest.getUsername().isEmpty()) {
                            message += " username";
                        }
                        if (loginRequest.getAccessToken().isEmpty()) {
                            message += " accessToken";
                        }
                        message += "!";
                        response = mapper.readValue("{\"status\":\"error\",\"message\":\"" + message + "\"}", ErrorResponse.class);
                    } else {
                        response = mapper.readValue("{\"status\":\"ok\",\"token\":\"abc123\"}", LoginResponse.class);
                    }
                    callback.onResponse(null, Response.success(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
