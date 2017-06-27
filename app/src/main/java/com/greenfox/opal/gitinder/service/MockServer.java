package com.greenfox.opal.gitinder.service;

import com.greenfox.opal.gitinder.ApiService;
import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.model.response.LoginResponse;
import com.greenfox.opal.gitinder.model.response.MatchesResponse;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;

public class MockServer implements ApiService {
    @Override
    public MockCall<LoginResponse> login(@Body final LoginRequest loginRequest) {
        return new MockCall<LoginResponse>() {
            @Override
            public void enqueue(Callback callback) {
                LoginResponse response;
                if (loginRequest.getUsername().isEmpty() || loginRequest.getAccessToken().isEmpty()) {
                    String message = "Missing parameter(s):";
                    if (loginRequest.getUsername().isEmpty()) {
                        message += " username";
                    }
                    if (loginRequest.getAccessToken().isEmpty()) {
                        message += " accessToken";
                    }
                    message += "!";
                    response = new LoginResponse(message);
                } else {
                    response = new LoginResponse(loginRequest.getUsername(), loginRequest.getAccessToken());
                }
                callback.onResponse(null, Response.success(response));
            }
        };
    }

    @Override
    public MockCall<MatchesResponse> getMatches(@Header("X-GiTinder-token") final String token) {
        return new MockCall<MatchesResponse>() {
            @Override
            public void enqueue(Callback callback) {
                MatchesResponse response;
                if (token.isEmpty()) {
                    response = new MatchesResponse("Unauthorized request!");
                } else {
                    response = new MatchesResponse();
                }
                callback.onResponse(null, Response.success(response));
            }
        };
    }
}
