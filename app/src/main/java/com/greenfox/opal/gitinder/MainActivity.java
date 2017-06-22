package com.greenfox.opal.gitinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.greenfox.opal.gitinder.fragments.MatchesFragment;
import com.greenfox.opal.gitinder.fragments.SettingsFragment;
import com.greenfox.opal.gitinder.fragments.SwipingFragment;
import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.model.response.LoginResponse;
import com.greenfox.opal.gitinder.service.MockServer;
import com.greenfox.opal.gitinder.service.SectionsPagerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";
  SectionsPagerAdapter mSectionsPagerAdapter;
  ViewPager mViewPager;
  ApiService service;
  Retrofit retrofit;
  boolean connectToBackend = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Log.d(TAG, "starting MainActivity");

    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayShowHomeEnabled(true);

    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
    mViewPager = (ViewPager) findViewById(R.id.container);
    setupViewPager(mViewPager);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(mViewPager);

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

    checkLogin();
  }

  public void setupViewPager(ViewPager viewPager) {
    SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(new SwipingFragment(), getString(R.string.swiping_tab_title));
    adapter.addFragment(new MatchesFragment(), getString(R.string.matches_tab_title));
    adapter.addFragment(new SettingsFragment(), getString(R.string.settings_tab_title));
    viewPager.setAdapter(adapter);
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
