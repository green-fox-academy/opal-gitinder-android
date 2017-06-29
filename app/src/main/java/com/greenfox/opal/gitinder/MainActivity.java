package com.greenfox.opal.gitinder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.Toast;

import com.greenfox.opal.gitinder.fragments.MatchesFragment;
import com.greenfox.opal.gitinder.fragments.SettingsFragment;
import com.greenfox.opal.gitinder.fragments.SwipingFragment;
import com.greenfox.opal.gitinder.service.ApiService;
import com.greenfox.opal.gitinder.service.NonSwipeableViewPager;
import com.greenfox.opal.gitinder.service.SectionsPagerAdapter;

import java.sql.Time;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

  SectionsPagerAdapter mSectionsPagerAdapter;
  NonSwipeableViewPager mViewPager;
  ConnectivityManager cm;

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

    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
    mViewPager = (NonSwipeableViewPager) findViewById(R.id.container);
    setupViewPager(mViewPager);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(mViewPager);

    cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null &&
        activeNetwork.isConnectedOrConnecting();
    Toast toast = Toast.makeText(this, (isConnected)?"Connected":"Not Connected =(", Toast.LENGTH_LONG);

    checkLogin();
    toast.show();
  }

  public void setupViewPager(ViewPager viewPager) {
    SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(new SwipingFragment(), getString(R.string.swiping_tab_title));
    adapter.addFragment(new MatchesFragment(), getString(R.string.matches_tab_title));
    adapter.addFragment(new SettingsFragment(), getString(R.string.settings_tab_title));
    viewPager.setAdapter(adapter);
  }

  public void checkLogin() {
    String username = preferences.getString("Username", null);

    if (TextUtils.isEmpty(username)) {
      Intent intent = new Intent(this, LoginActivity.class);
      startActivity(intent);
    }
  }
}
