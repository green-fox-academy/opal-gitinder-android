package com.greenfox.opal.gitinder;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
        setContentView(R.layout.activity_main);

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

        checkLogin();
    }

    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }

    public void checkLogin() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String username = preferences.getString("Username", null);

        if (TextUtils.isEmpty(username)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
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
