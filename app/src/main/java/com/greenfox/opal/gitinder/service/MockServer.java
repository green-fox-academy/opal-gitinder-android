package com.greenfox.opal.gitinder.service;

import com.greenfox.opal.gitinder.ApiService;
import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.model.StatusResponse;

import java.io.IOException;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class MockServer implements ApiService {
    @Override
    public Call<StatusResponse> login(@Body final LoginRequest loginRequest) {
        return new Call<StatusResponse>() {
            @Override
            public Response<StatusResponse> execute() throws IOException {
                return null;
            }

            @Override
            public void enqueue(Callback<StatusResponse> callback) {
                StatusResponse response;
                if (loginRequest.getUsername().isEmpty() || loginRequest.getAccessToken().isEmpty()) {
                    String message = "Missing parameter(s):";
                    if (loginRequest.getUsername().isEmpty()) {
                        message += " username";
                    }
                    if (loginRequest.getAccessToken().isEmpty()) {
                        message += "accessToken";
                    }
                    message += "!";
                    response = new StatusResponse("error", message);
                } else {
                    response = new StatusResponse("ok", "abc123");
                }
                callback.onResponse(null, Response.success(response));
            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<StatusResponse> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
    }
}
