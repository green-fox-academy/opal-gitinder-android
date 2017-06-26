package com.greenfox.opal.gitinder;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.model.response.LoginResponse;
import com.wuman.android.auth.AuthorizationDialogController;
import com.wuman.android.auth.AuthorizationFlow;
import com.wuman.android.auth.DialogFragmentController;
import com.wuman.android.auth.OAuthManager;
import com.wuman.android.auth.OAuthManager.OAuthCallback;
import com.wuman.android.auth.OAuthManager.OAuthFuture;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

  //@Inject ApiService apiService
  //@Inject SharedPreferences preferences;
  ApiService service;
  SharedPreferences preferences;
  SharedPreferences.Editor editor;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayShowHomeEnabled(true);

    onLogin("Bond", "abcd1234");
    onLogin("", "");
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
  }

  public void authentication() {
    AuthorizationFlow flow = buildAuthorizationFlow();
    AuthorizationDialogController controller = createGitHubControllerHandler();

    OAuthManager oAuthManager = new OAuthManager(flow, controller);
    oAuthManager.authorizeExplicitly("userID", new OAuthCallback<Credential>() {
      @Override
      public void run(OAuthFuture<Credential> future) {
        try {
          Log.d("success", future.getResult().getAccessToken());
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

  public void onLogin(String username, String token) {
    LoginRequest testLogin = new LoginRequest(username, token);
    service.login(testLogin).enqueue(new Callback<LoginResponse>() {
      @Override
      public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
        if (response.body().getStatus().equals("ok")) {
          Log.d("dev", response.body().getToken());
          saveLoginData(response.body().getToken());
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

  protected void saveLoginData(String token) {
    editor.putString("Token", token);
    editor.putString("Username", null);
    editor.apply();
  }
}
