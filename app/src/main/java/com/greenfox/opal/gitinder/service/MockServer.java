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
        if (loginRequest.getUsername().equals("Bond") && loginRequest.getAccessToken().equals("abcd1234")) {
            return new Call<StatusResponse>() {
                @Override
                public Response<StatusResponse> execute() throws IOException {
                    StatusResponse response = new StatusResponse("ok", "abc123");
                    return Response.success(response);
                }

                @Override
                public void enqueue(Callback<StatusResponse> callback) {
                    // ezt használd
                    loginRequest.getUsername();
                    callback.onResponse(null, Response.success(new StatusResponse("ok", "abc123")));
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
        return new Call<StatusResponse>() {
            @Override
            public Response<StatusResponse> execute() throws IOException {
                StatusResponse response = new StatusResponse("error", "Missing parameter(s): username!");
                return Response.success(response);
            }

            @Override
            public void enqueue(Callback<StatusResponse> callback) {

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
