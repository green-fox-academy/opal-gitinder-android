package com.greenfox.opal.gitinder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfox.opal.gitinder.ApiService;
import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.response.LoginResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class MockServer implements ApiService {
    @Override
    public Call login(@Body final LoginRequest loginRequest) {
        return new MockCall() {
            @Override
            public void enqueue(Callback callback) {
                LoginResponse response;
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
                        response = mapper.readValue("{\"status\":\"error\",\"message\":\"" + message + "\"}", LoginResponse.class);
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
