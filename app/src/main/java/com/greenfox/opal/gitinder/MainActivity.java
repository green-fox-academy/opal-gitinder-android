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
import com.greenfox.opal.gitinder.model.response.Profile;
import com.greenfox.opal.gitinder.service.MockServer;
import com.greenfox.opal.gitinder.service.SectionsPagerAdapter;
import java.util.ArrayList;
import com.greenfox.opal.gitinder.model.response.ProfileListResponse;
import java.util.List;
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
    onListRequest("abcd1234", 0);
    onListRequest("", 0);
    onListRequest(null, null);
    onLogin("Bond", "abcd1234");
    onLogin("", "");

    checkLogin();

    ArrayList<String> repos = new ArrayList<>();
    repos.add("opal-gitinder-android");
    ArrayList<String> languages = new ArrayList<>();
    languages.add("Java");

//    ProfileAdapter adapter = new ProfileAdapter(this);
//    View fragment = findViewById(R.id.matchesFragment);
//    ListView listView = (ListView)fragment.findViewById(R.id.matchList);
//    listView.setAdapter(adapter);

//    adapter.add(new Profile("Garlyle", "", repos, languages));
//    adapter.add(new Profile("balintvecsey", "", repos, languages));
//    adapter.add(new Profile("dorinagy", "", repos, languages));

//    swiping fragment
//    FragmentManager fragmentManager = getFragmentManager();
//    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//    SwipingFragment swipingFragment = new SwipingFragment();
//    fragmentTransaction.add(R.id.swiping_container, swipingFragment);
//    fragmentTransaction.commit();
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
      public void onResponse(Call<ProfileListResponse> call,
          Response<ProfileListResponse> response) {
        if (response.body().getStatus() != null) {
          Log.d("dev", response.body().getMessage());
        } else {
          List<Profile> members = response.body().getProfiles();
          for (Profile p : members) {
            Log.d("dev", p.getLogin() + ":" + p.getAvatarUrl() + ":" + p.getRepos() + ":" + p
                .getLanguages());
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
