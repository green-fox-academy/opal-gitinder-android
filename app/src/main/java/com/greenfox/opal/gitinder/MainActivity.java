package com.greenfox.opal.gitinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.response.ErrorResponse;
import com.greenfox.opal.gitinder.response.LoginResponse;
import com.greenfox.opal.gitinder.response.StatusResponse;
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
    }

    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }

    public void onLogin(String username, String token) {
        LoginRequest testLogin = new LoginRequest(username, token);
        service.login(testLogin).enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.body() instanceof LoginResponse) {
                    LoginResponse loginResponse = (LoginResponse)response.body();
                    Log.i("login", loginResponse.getToken());
                } else {
                    ErrorResponse errorResponse = (ErrorResponse)response.body();
                    Log.e("login", errorResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Log.e("login", "FAIL! =(");
            }
        });
    }
}
