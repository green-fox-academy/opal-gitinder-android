package com.greenfox.opal.gitinder;

import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.model.StatusResponse;
import com.greenfox.opal.gitinder.service.MockCall;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/login")
    MockCall login(@Body LoginRequest loginRequest);
}
