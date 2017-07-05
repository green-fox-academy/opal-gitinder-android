package com.greenfox.opal.gitinder;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;

import com.greenfox.opal.gitinder.fragments.MatchesFragment;
import com.greenfox.opal.gitinder.fragments.SettingsFragment;
import com.greenfox.opal.gitinder.fragments.SwipingFragment;
import com.greenfox.opal.gitinder.service.ApiService;

import com.greenfox.opal.gitinder.service.NonSwipeableViewPager;
import com.greenfox.opal.gitinder.service.SectionsPagerAdapter;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

  public static final String APP_STATE = "AppState";
  SectionsPagerAdapter mSectionsPagerAdapter;
  NonSwipeableViewPager mViewPager;
  String timestamp;
  SharedPreferences.Editor editor;

  @Inject
  SharedPreferences preferences;
  @Inject
  ApiService service;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    GitinderApp.app().basicComponent().inject(this);

    if(checkLogin()) {
      ActionBar actionBar = getSupportActionBar();
      actionBar.setDisplayShowHomeEnabled(true);

      mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
      mViewPager = (NonSwipeableViewPager) findViewById(R.id.container);
      setupViewPager(mViewPager);

      TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
      tabLayout.setupWithViewPager(mViewPager);
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    saveOnPause();
  }

  @Override
  protected void onStop() {
    super.onStop();
    saveOnPause();
  }

  public void saveOnPause() {
    editor = preferences.edit();
    timestamp = String.valueOf(System.currentTimeMillis());
    editor.putString(APP_STATE, timestamp);
    editor.apply();
  }


  public void setupViewPager(ViewPager viewPager) {
    SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(new SwipingFragment(), getString(R.string.swiping_tab_title));
    adapter.addFragment(new MatchesFragment(), getString(R.string.matches_tab_title));
    adapter.addFragment(new SettingsFragment(), getString(R.string.settings_tab_title));
    viewPager.setAdapter(adapter);
  }

  public boolean checkLogin() {
    String username = preferences.getString("Username", null);
    String githubAccessToken = preferences.getString("Github Access Token", null);
    String backendResponseToken = preferences.getString("Backend Response Token", null);

    if (TextUtils.isEmpty(username) || TextUtils.isEmpty(githubAccessToken) || TextUtils.isEmpty(backendResponseToken)) {
      Intent intent = new Intent(this, LoginActivity.class);
      startActivity(intent);
      return false;
    }
    return true;
  }
}
