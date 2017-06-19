package com.greenfox.opal.gitinder.service;

import com.greenfox.opal.gitinder.ApiService;
import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.response.LoginResponse;
import com.greenfox.opal.gitinder.response.ProfileListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Path;

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
    public Call<ProfileListResponse> getListOfTinders(@Header("X-GiTinder-token") String token, @Path("page") int page) {
        return null;
    }
}
