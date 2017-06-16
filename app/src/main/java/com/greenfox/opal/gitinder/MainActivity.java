package com.greenfox.opal.gitinder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;
import android.util.Log;
import android.widget.TextView;

import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.response.LoginResponse;
import com.greenfox.opal.gitinder.service.MockServer;

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
        onLogin("Bond", "abcd1234");
        onLogin("", "");


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
