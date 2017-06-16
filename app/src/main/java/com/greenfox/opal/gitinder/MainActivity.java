package com.greenfox.opal.gitinder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.gitinder_logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_main);

        TabHost host = (TabHost) findViewById(R.id.tabHost);
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

        //change tab color when selected
        for (int i = 0; i < host.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#000000"));
        }
        TextView tv = (TextView) host.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
        tv.setTextColor(Color.parseColor("#ff5719"));
    }
}
