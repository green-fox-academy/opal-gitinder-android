package com.greenfox.opal.gitinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.model.response.GithubUser;
import com.greenfox.opal.gitinder.model.response.LoginResponse;
import com.greenfox.opal.gitinder.service.ApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.greenfox.opal.gitinder.service.GithubApiService;
import com.wuman.android.auth.AuthorizationDialogController;
import com.wuman.android.auth.AuthorizationFlow;
import com.wuman.android.auth.DialogFragmentController;
import com.wuman.android.auth.OAuthManager;
import com.wuman.android.auth.OAuthManager.OAuthCallback;
import com.wuman.android.auth.OAuthManager.OAuthFuture;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class LoginActivity extends AppCompatActivity {

  SharedPreferences.Editor editor;
  @Inject
  ApiService service;
  @Inject
  SharedPreferences preferences;

  private final String USERNAME = "Username";
  private final String GITHUB_ACCESS_TOKEN = "Github Access Token";
  private final String BACKEND_RESPONSE_TOKEN = "Backend Response Token";
  private static final String TAG = "LoginActivity";

  Retrofit githubRetrofit;
  GithubApiService githubService;
  String username;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    Log.d(TAG, "starting LoginActivity");

    GitinderApp.app().basicComponent().inject(this);

    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayShowHomeEnabled(true);

    editor = preferences.edit();
  }

  @Override
  protected void onResume() {
    super.onResume();

    AlertDialog.Builder a_builder = new AlertDialog.Builder(LoginActivity.this);
    a_builder.setMessage(R.string.dialog_message)
      .setCancelable(false)
      .setPositiveButton(R.string.dialog_button_login, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          authentication();
        }
      })
      .setNegativeButton(R.string.dialog_button_exit, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          finishAffinity();
        }
      });
    AlertDialog alert = a_builder.create();
    alert.setTitle(R.string.dialog_title);
    alert.show();

    githubRetrofit = new Retrofit.Builder()
      .baseUrl("https://api.github.com")
      .addConverterFactory(JacksonConverterFactory.create())
      .build();
    githubService = githubRetrofit.create(GithubApiService.class);
  }

  public void authentication() {
    AuthorizationFlow flow = buildAuthorizationFlow();
    AuthorizationDialogController controller = createGitHubControllerHandler();

    OAuthManager oAuthManager = new OAuthManager(flow, controller);
    oAuthManager.authorizeExplicitly("userID", new OAuthCallback<Credential>() {
      @Override
      public void run(OAuthFuture<Credential> future) {
        try {
          String token = future.getResult().getAccessToken();
          userNameRequest(token);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }, null);
  }

  public AuthorizationFlow buildAuthorizationFlow() {
    AuthorizationFlow.Builder builder = new AuthorizationFlow.Builder(
      BearerToken.authorizationHeaderAccessMethod(),
      AndroidHttp.newCompatibleTransport(),
      new JacksonFactory(),
      new GenericUrl("https://github.com/login/oauth/access_token"),
      new ClientParametersAuthentication(getResources().getString(R.string.CLIENT_ID), getResources().getString(R.string.CLIENT_SECRET)),
      getResources().getString(R.string.CLIENT_ID),
      "http://github.com/login/oauth/authorize");
    builder.setRequestInitializer(new HttpRequestInitializer() {
      @Override
      public void initialize(HttpRequest request) throws IOException {
        request.getHeaders().setAccept("application/json");
      }
    });

    AuthorizationFlow flow = builder.build();
    return flow;
  }

  public AuthorizationDialogController createGitHubControllerHandler() {
    AuthorizationDialogController controller =
      new DialogFragmentController(getFragmentManager()) {
        @Override
        public String getRedirectUri() throws IOException {
          return "http://gitinder.herokuapp.com/callback";
        }

        @Override
        public boolean isJavascriptEnabledForWebView() {
          return true;
        }

        @Override
        public boolean disableWebViewCache() {
          return false;
        }

        @Override
        public boolean removePreviousCookie() {
          return false;
        }
      };

    return controller;
  }

  public void userNameRequest(final String token) {
    githubService.getUser("token " + token).enqueue(new Callback<GithubUser>() {
      @Override
      public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
        if (response.body().getStatus() != null) {
          Log.d("dev", response.body().getMessage());
        } else {
          username = response.body().getLogin();
          onLogin(username, token);
        }
      }

      @Override
      public void onFailure(Call<GithubUser> call, Throwable t) {
        Log.d("dev", "FAIL! =(");
      }
    });
  }

  public void onLogin(final String username, final String githubAccessToken) {
    LoginRequest testLogin = new LoginRequest(username, githubAccessToken);
      service.login(testLogin).enqueue(new Callback<LoginResponse>() {
      @Override
      public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
        if (response.body().getStatus().equals("ok")) {
          String backendResponseToken = response.body().getToken();
          saveLoginData(username, githubAccessToken, backendResponseToken);
          Intent intent = new Intent(LoginActivity.this, MainActivity.class);
          startActivity(intent);
        } else {
          Log.d("dev", response.body().getMessage());
        }
      }

      @Override
      public void onFailure(Call<LoginResponse> call, Throwable t) {
        Toast.makeText(LoginActivity.this, "login error", Toast.LENGTH_SHORT).show();
        Log.d("login", "FAIL! =(");
      }
    });
  }

  protected void saveLoginData(String username, String githubAccessToken, String backendResponseToken) {
    editor.putString(GITHUB_ACCESS_TOKEN, githubAccessToken);
    editor.putString(USERNAME, username);
    editor.putString(BACKEND_RESPONSE_TOKEN, backendResponseToken);
    editor.apply();
  }
}
