package com.greenfox.opal.gitinder;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.greenfox.opal.gitinder.fragment.SwipingFragment;
import com.greenfox.opal.gitinder.service.MockServer;

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
        actionBar.setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_main);

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


        if (connectToBackend) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://gitinder.herokuapp.com/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            service = retrofit.create(ApiService.class);
        } else {
            service = new MockServer();
        }

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
}
