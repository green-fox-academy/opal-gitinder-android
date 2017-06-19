package com.greenfox.opal.gitinder.service;

import com.greenfox.opal.gitinder.ApiService;
import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.model.Profile;
import com.greenfox.opal.gitinder.response.LoginResponse;
import com.greenfox.opal.gitinder.response.ProfileListResponse;

import java.util.ArrayList;

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
    public MockCall<ProfileListResponse> getListOfTinders(@Header("X-GiTinder-token") final String token, @Path("page") int page) {
        return new MockCall<ProfileListResponse>() {
            @Override
            public void enqueue(Callback callback) {
                ProfileListResponse response;
                if (token.isEmpty()) {
                    response = new ProfileListResponse("Unauthorized request!");
                } else {
                    ArrayList<Profile> list = new ArrayList<>();
                    list.add(new Profile("garlyle", "funny.jpg", "opal-gitinder-android", "Java"));
                    list.add(new Profile("balintvecsey", "quiet.jpg", "opal-gitinder-android", "Java"));
                    list.add(new Profile("dorinagy", "smiley.jpg", "opal-gitinder-android", "Java"));
                    response = new ProfileListResponse(list, list.size(), 42);
                }
                callback.onResponse(null, Response.success(response));
            }
        };
    }
}
