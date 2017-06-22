package com.greenfox.opal.gitinder.service;

import com.greenfox.opal.gitinder.model.response.Profile;

import com.greenfox.opal.gitinder.ApiService;
import com.greenfox.opal.gitinder.Direction;
import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.model.response.BaseResponse;
import com.greenfox.opal.gitinder.model.response.LoginResponse;

import java.util.ArrayList;
import java.util.List;

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
			public void enqueue(Callback<LoginResponse> callback) {
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
	public MockCall<Profile> getProfileInfos(@Header("X-GiTinder-token") final String token) {
		return new MockCall<Profile>() {
			@Override
			public void enqueue(Callback<Profile> callback) {
				Profile response;
				if (token == null || "".equals(token)) {
					response = new Profile("Unauthorized request!");
				} else {
					List<String> repos = new ArrayList<>();
					List<String> languages = new ArrayList<>();
					repos.add("opal-gitinder-android");
					languages.add("Java");
					response = new Profile("happysloth", "happysloth.png", repos, languages);
				}
				callback.onResponse(null, Response.success(response));
			}
		};
	}

	@Override
	public MockCall<Profile> swiping(@Header(value = "X-GiTinder-token") final String token,
	                                 @Path("username") String username,
	                                 @Path("direction") Enum<Direction> direction) {
		return new MockCall<Profile>() {
			@Override
			public void enqueue(Callback callback) {
				BaseResponse response;
				if (token.isEmpty()) {
					response = new BaseResponse("error", "token is empty");
				} else {
					response = new BaseResponse("ok", "success");
				}
				callback.onResponse(null, Response.success(response));
			}
		};
	}
}
