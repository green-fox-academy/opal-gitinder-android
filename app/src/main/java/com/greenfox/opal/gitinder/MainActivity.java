package com.greenfox.opal.gitinder;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.greenfox.opal.gitinder.fragment.SwipingFragment;
import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.service.ApiService;
import com.greenfox.opal.gitinder.model.response.LoginResponse;

import com.greenfox.opal.gitinder.model.response.Profile;
import com.greenfox.opal.gitinder.model.response.ProfileListResponse;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  @Inject
  SharedPreferences preferences;
  @Inject
  ApiService service;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    GitinderApp.app().basicComponent().inject(this);

    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayShowHomeEnabled(true);

    //swiping fragment
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    SwipingFragment swipingFragment = new SwipingFragment();
    fragmentTransaction.add(R.id.swiping_container, swipingFragment);
    fragmentTransaction.commit();

    TabHost host = (TabHost) findViewById(R.id.tabHost);
    host.setup();

    //Tab 1
    TabHost.TabSpec spec = host.newTabSpec(getResources().getString(R.string.swiping_tab_title));
    spec.setContent(R.id.swiping_container);
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

    onListRequest("abcd1234", 0);
    onListRequest("", 0);
    onListRequest(null, null);

    onLogin("Bond", "abcd1234");
    onLogin("", "");

    checkLogin();
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

  public void onListRequest(String token, Integer page) {
    service.getListOfTinders(token, page).enqueue(new Callback<ProfileListResponse>() {
      @Override
      public void onResponse(Call<ProfileListResponse> call, Response<ProfileListResponse> response) {
        if (response.body().getStatus() != null) {
          Log.d("dev", response.body().getMessage());
        } else {
          List<Profile> members = response.body().getProfiles();
          for (Profile p : members) {
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
