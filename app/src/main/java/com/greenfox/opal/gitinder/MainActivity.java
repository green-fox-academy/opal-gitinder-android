package com.greenfox.opal.gitinder;

import android.os.Bundle;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import android.util.Log;

import android.widget.TextView;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.model.Profile;
import com.greenfox.opal.gitinder.response.LoginResponse;
import com.greenfox.opal.gitinder.response.ProfileListResponse;
import com.greenfox.opal.gitinder.service.MockServer;
import com.wuman.android.auth.AuthorizationDialogController;
import com.wuman.android.auth.AuthorizationFlow;
import com.wuman.android.auth.DialogFragmentController;
import com.wuman.android.auth.OAuthManager;

import com.wuman.android.auth.OAuthManager.OAuthCallback;
import com.wuman.android.auth.OAuthManager.OAuthFuture;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ApiService service;
    Retrofit retrofit;
    boolean connectToBackend = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.gitinder_logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_main);

        if (connectToBackend) {
            retrofit = new Retrofit.Builder()
                .baseUrl("http://gitinder.herokuapp.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

            service = retrofit.create(ApiService.class);
        } else {
           service = new MockServer();
        }

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

        if (connectToBackend) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://gitinder.herokuapp.com/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            service = retrofit.create(ApiService.class);
        } else {
            service = new MockServer();
        }
        onListRequest("abcd1234", 0);
        onLogin("Bond", "abcd1234");
        onLogin("", "");

        checkLogin();

        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("SWIPING");
        spec.setContent(R.id.tab1);
        spec.setIndicator("SWIPING");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("MATCHES");
        spec.setContent(R.id.tab2);
        spec.setIndicator("MATCHES");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("SETTINGS");
        spec.setContent(R.id.tab3);
        spec.setIndicator("SETTINGS");
        host.addTab(spec);

        //change tab color when selected
        for (int i = 0; i < host.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#000000"));
        }
        TextView tv = (TextView) host.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
        tv.setTextColor(Color.parseColor("#ff5719"));
    }

    public void checkLogin() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String clientID = preferences.getString("ClientID", null);

        if (TextUtils.isEmpty(clientID)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void onLogin(String username, String token) {
        LoginRequest testLogin = new LoginRequest(username, token);
        service.login(testLogin).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().getStatus().equals("ok")) {
                    Log.d("dev", response.body().getToken());
                } else {
                    Log.d("dev", response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("login", "FAIL! =(");
            }
        });
    }

    public void onListRequest(String token, int page) {
        service.getListOfTinders(token, page).enqueue(new Callback<ProfileListResponse>() {
            @Override
            public void onResponse(Call<ProfileListResponse> call, Response<ProfileListResponse> response) {
                if (response.body().getStatus() != null) {
                    Log.d("dev", response.body().getMessage());
                } else {
                    List<Profile> members = response.body().getProfiles();
                    for(Profile p : members) {
                        Log.d("dev", p.getLogin() + ":" + p.getAvatarUrl() + ":" + p.getRepos() + ":" + p.getLanguages());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileListResponse> call, Throwable t) {
                Log.d("dev", "FAIL! =(");
            }
        });
    }
}
