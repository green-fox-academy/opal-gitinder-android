package com.greenfox.opal.gitinder;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.service.ApiService;
import com.greenfox.opal.gitinder.model.response.LoginResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  @Inject SharedPreferences preferences;
  @Inject ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GitinderApp.app().basicComponent().inject(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);

        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec(getResources().getString(R.string.swiping_tab_title));
        spec.setContent(R.id.tab1);
        spec.setIndicator(getString(R.string.swiping_tab_title));
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec(getResources().getString(R.string.matches_tab_title));
        spec.setContent(R.id.tab2);
        spec.setIndicator(getString(R.string.matches_tab_title));
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec(getResources().getString(R.string.settings_tab_title));
        spec.setContent(R.id.tab3);
        spec.setIndicator(getString(R.string.settings_tab_title));
        host.addTab(spec);

        onLogin("Bond", "abcd1234");
        onLogin("", "");
      
        checkLogin();
    }

    public void checkLogin() {
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
                    Log.d("login", response.body().getToken());
                } else {
                    Log.d("login", response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("login", "FAIL! =(");
            }
        });
    }
}
