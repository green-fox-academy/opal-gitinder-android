package com.greenfox.opal.gitinder.service;

import com.greenfox.opal.gitinder.model.response.GithubUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GithubApiService {

  @GET("/user")
  Call<GithubUser> getUser(@Header("Authorization") String accsessToken);
}
