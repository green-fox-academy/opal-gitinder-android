package com.greenfox.opal.gitinder.service;

import com.greenfox.opal.gitinder.model.ExtendedMessage;
import com.greenfox.opal.gitinder.model.Message;
import com.greenfox.opal.gitinder.model.response.MessageResponse;
import com.greenfox.opal.gitinder.model.response.PostMessageResponse;
import com.greenfox.opal.gitinder.model.response.Profile;
import com.greenfox.opal.gitinder.Direction;
import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.model.response.BaseResponse;
import com.greenfox.opal.gitinder.model.response.LoginResponse;
import com.greenfox.opal.gitinder.model.response.Match;
import com.greenfox.opal.gitinder.model.response.MatchesResponse;
import com.greenfox.opal.gitinder.model.response.StatusResponse;
import com.greenfox.opal.gitinder.model.response.SwipingResponse;
import com.greenfox.opal.gitinder.model.response.ProfileListResponse;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Path;

public class MockServer implements ApiService {
  public final static String mockToken = "abcd1234";
  public final static String CREEPY_URL = "https://pbs.twimg.com/profile_images/658567330566414337/xVR-6ohi_400x400.jpg";
  public final static String THINKER_URL = "https://www.quizz.biz/uploads/quizz/975627/11_7DfU5.jpg";
  public final static String HUNGRY_URL = "http://www.rainforest-alliance.org/sites/default/files/styles/750w_585h/public/2016-09/three-toed-sloth.jpg";

  @Override
  public Call<LoginResponse> login(@Header("Content-Type") String content_type, @Body final LoginRequest loginRequest) {
    return new MockCall<LoginResponse>() {
      @Override
      public void enqueue(Callback<LoginResponse> callback) {
        LoginResponse response;
        if (loginRequest.getUser_name().isEmpty() || loginRequest.getAccess_token().isEmpty()) {
          String message = "Missing parameter(s):";
          if (loginRequest.getUser_name().isEmpty()) {
            message += " username";
          }
          if (loginRequest.getAccess_token().isEmpty()) {
            message += " accessToken";
          }
          message += "!";
          response = new LoginResponse(message);
        } else {
          response = new LoginResponse("ok", mockToken);
        }
        callback.onResponse(null, Response.success(response));
      }
    };
  }

  @Override
  public MockCall<ProfileListResponse> getListOfTinders(@Header("X-GiTinder-token") final String token, @Path("page") Integer page) {
    return new MockCall<ProfileListResponse>() {
      @Override
      public void enqueue(Callback<ProfileListResponse> callback) {
        ProfileListResponse response;
        if (token == null) {
          response = new ProfileListResponse("Unauthorized request!");
        } else {
          ArrayList<Profile> list = new ArrayList<>();
          ArrayList<String> repos = new ArrayList<>();
          ArrayList<String> languages = new ArrayList<>();
          repos.add("opal-gitinder-android");
          languages.add("Java");
          list.add(new Profile("garlyle", THINKER_URL, repos, languages));
          list.add(new Profile("balintvecsey", CREEPY_URL, repos, languages));
          list.add(new Profile("dorinagy", HUNGRY_URL, repos, languages));
          response = new ProfileListResponse(list, list.size(), 42);
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
        if (token == null) {
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
  public MockCall<SwipingResponse> swiping(@Header(value = "X-GiTinder-token") final String token,
                                           @Path("username") String username,
                                           @Path("direction") final Enum<Direction> direction) {
    return new MockCall<SwipingResponse>() {
      @Override
      public void enqueue(Callback callback) {
        BaseResponse response;
        if (token.isEmpty()) {
          response = new SwipingResponse("error", "empty token");
        } else if(direction.equals(RIGHT)){
          ArrayList<String> messages = new ArrayList<>(Arrays.asList("Latest Message", "Other Message"));
          response = new SwipingResponse(new Match("Garlyle2", "thinker", System.currentTimeMillis(), messages));
        } else {
          response = new SwipingResponse("ok", "success");
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
          ArrayList<String> messages = new ArrayList<>(Arrays.asList("Latest Message", "Other Message"));
          ArrayList<Match> matches = new ArrayList<>();
          matches.add(new Match("Garlyle", THINKER_URL, System.currentTimeMillis(), messages));
          matches.add(new Match("balintvecsey", CREEPY_URL, System.currentTimeMillis(), messages));
          matches.add(new Match("dorinagy", HUNGRY_URL, System.currentTimeMillis(), messages));

          response = new MatchesResponse(matches);
        }
        callback.onResponse(null, Response.success(response));
      }
    };
  }


  @Override
  public Call<MessageResponse> getMessages(@Header(value = "X-GiTinder-token") final String token, @Path("username") final String username) {
    return new MockCall<MessageResponse>() {
      @Override
      public void enqueue(Callback callback) {
        MessageResponse response;
        if (token.isEmpty()) {
          response = new MessageResponse("Unauthorized request!");
        } else {
          ArrayList<ExtendedMessage> messages = new ArrayList<>();
          messages.add(new ExtendedMessage("balintvecsey", "first message", 1738, username, System.currentTimeMillis()));
          messages.add(new ExtendedMessage("balintvecsey", "second message", 49820, username, System.currentTimeMillis()));
          messages.add(new ExtendedMessage("balintvecsey", "last message", 495809, username, System.currentTimeMillis()));

          response = new MessageResponse(messages);
          response.setStatus("ok");
        }
        callback.onResponse(null, Response.success(response));
      }
    };
  }

  @Override
  public Call<PostMessageResponse> postMessage(@Header(value = "X-GiTinder-token") final String token, @Body Message message) {
    return new MockCall<PostMessageResponse>() {
      @Override
      public void enqueue(Callback callback) {
        PostMessageResponse response;
        if (token.isEmpty()) {
          response = new PostMessageResponse("Unauthorized request!");
        } else {
          ExtendedMessage message = new ExtendedMessage("dorinagy", "new message", 495809, "balintvecsey", System.currentTimeMillis());

          response = new PostMessageResponse(message);
        }
        callback.onResponse(null, Response.success(response));
      }
    };
  }

  @Override
  public Call<StatusResponse> deletMessage(@Header(value = "X-GiTinder-token") final String token, @Path("id") Long id) {
    return new MockCall<StatusResponse>() {
      @Override
      public void enqueue(Callback callback) {
        StatusResponse response;
        if (token.isEmpty()) {
          response = new BaseResponse("error", "Unauthorized request!");
        } else {
          response = new StatusResponse("ok");
        }
        callback.onResponse(null, Response.success(response));
      }
    };
  }
}
