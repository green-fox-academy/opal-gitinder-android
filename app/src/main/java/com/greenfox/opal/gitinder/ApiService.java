package com.greenfox.opal.gitinder;

import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.model.response.LoginResponse;
import com.greenfox.opal.gitinder.model.response.MatchesResponse;
import com.greenfox.opal.gitinder.service.MockCall;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("/matches")
    MockCall<MatchesResponse> getMatches(@Header("X-GiTinder-token") String token);
}
