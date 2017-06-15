package com.greenfox.opal.gitinder;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.gitinder_logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_main);

        TabHost host = (TabHost)findViewById(R.id.tabHost);
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
    }
}
