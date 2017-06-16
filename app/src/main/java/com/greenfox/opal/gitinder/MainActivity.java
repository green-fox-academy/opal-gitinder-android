package com.greenfox.opal.gitinder;

<<<<<<< HEAD
import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.os.Handler;
=======
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
>>>>>>> dori-mainlayout
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;
import android.widget.TextView;

import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.model.OnError;
import com.greenfox.opal.gitinder.model.StatusResponse;
import com.greenfox.opal.gitinder.service.OnTokenAcquired;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ApiService service;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.gitinder_logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_main);
<<<<<<< HEAD

        retrofit = new Retrofit.Builder()
                .baseUrl("http://gitinder.herokuapp.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);

        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccountsByType("com.github.auth.login");
        Account myAccount = null;
        Bundle options = new Bundle();

        am.getAuthToken(
                myAccount,
                "Manage your tasks",
                options,
                this,
                new OnTokenAcquired(),
                new Handler((Handler.Callback) new OnError()) {
                });
    }
=======
>>>>>>> dori-mainlayout

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
        service.login(testLogin).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.body().getStatus().equals("ok")) {
                    System.out.println(response.body().getToken());
                } else {
                    System.out.println(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                System.out.println("FAIL! =( ");
            }
        });
    }
}
