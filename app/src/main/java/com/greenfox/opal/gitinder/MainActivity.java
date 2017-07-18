package com.greenfox.opal.gitinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.greenfox.opal.gitinder.fragments.MatchesFragment;
import com.greenfox.opal.gitinder.fragments.SettingsFragment;
import com.greenfox.opal.gitinder.fragments.SwipingFragment;
import com.greenfox.opal.gitinder.service.ApiService;
import com.greenfox.opal.gitinder.service.MatchesBroadcast;
import com.greenfox.opal.gitinder.service.NonSwipeableViewPager;
import com.greenfox.opal.gitinder.service.SectionsPagerAdapter;

import javax.inject.Inject;

import static android.provider.Settings.ACTION_WIFI_SETTINGS;
import static com.greenfox.opal.gitinder.LoginActivity.GITHUB_ACCESS_TOKEN;
import static com.greenfox.opal.gitinder.LoginActivity.USERNAME;
import static com.greenfox.opal.gitinder.LoginActivity.X_GITINDER_TOKEN;

public class MainActivity extends AppCompatActivity {

  public static final String APP_STATE = "AppState";
  private final String CHECK_SETTINGS = "Check Settings";

  String timestamp;
  SectionsPagerAdapter mSectionsPagerAdapter;
  NonSwipeableViewPager mViewPager;
  SharedPreferences.Editor editor;

  @Inject
  SharedPreferences preferences;
  @Inject
  ApiService service;

  AlarmManager alarmManager;
  PendingIntent pendingIntent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    GitinderApp.app().basicComponent().inject(this);

    checkConnection();
    if (checkLogin()) {
      ActionBar actionBar = getSupportActionBar();
      actionBar.setDisplayShowHomeEnabled(true);

      mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
      mViewPager = (NonSwipeableViewPager) findViewById(R.id.container);
      setupViewPager(mViewPager);

      Intent intent = new Intent(this, MatchesBroadcast.class);
      pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, 0);
      alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

      TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
      tabLayout.setupWithViewPager(mViewPager);
    }
  }

  private void checkConnection() {
    ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
    if (!isConnected) {
      AlertDialog alertDialog = new AlertDialog.Builder(this).create();
      alertDialog.setTitle(getString(R.string.no_connection_title));
      alertDialog.setMessage(getString(R.string.no_connection_message));
      alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
      alertDialog.setButton(CHECK_SETTINGS, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          startActivityForResult(new Intent(ACTION_WIFI_SETTINGS), 0);
        }
      });
      alertDialog.show();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    saveOnPause();
    if (alarmManager != null) {
      alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, 0, 600000, pendingIntent);
    }
  }

  @Override
  protected void onStop() {
    super.onStop();
    saveOnPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (alarmManager != null) {
      alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, 0, 60000, pendingIntent);
    }
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
    String username = preferences.getString(USERNAME, null);
    String githubAccessToken = preferences.getString(GITHUB_ACCESS_TOKEN, null);
    String backendResponseToken = preferences.getString(X_GITINDER_TOKEN, null);

    if (TextUtils.isEmpty(username) || TextUtils.isEmpty(githubAccessToken) || TextUtils.isEmpty(backendResponseToken)) {
      Intent intent = new Intent(this, LoginActivity.class);
      startActivity(intent);
      return false;
    }
    return true;
  }
}
