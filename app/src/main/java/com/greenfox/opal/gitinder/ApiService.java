package com.greenfox.opal.gitinder;

import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.model.response.LoginResponse;
import com.greenfox.opal.gitinder.model.response.ProfileListResponse;
import com.greenfox.opal.gitinder.model.response.Profile;
import com.greenfox.opal.gitinder.model.response.SwipingResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
  @POST("/login")
  Call<LoginResponse> login(@Body LoginRequest loginRequest);

  @GET("/profile")
  Call<Profile> getProfileInfos(@Header("X-GiTinder-token") String token);

  @GET("/available/{page}")
  Call<ProfileListResponse> getListOfTinders(@Header("X-GiTinder-token") String token, @Path("page") Integer page);

  @PUT("/profiles/{username}/{direction}")
  Call<SwipingResponse> swiping(@Header(value = "X-GiTinder-token") String token, @Path("username") String username, @Path("direction") Enum<Direction> direction);

}
