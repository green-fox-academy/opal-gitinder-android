package com.greenfox.opal.gitinder;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;


public class MainActivityTabbed extends AppCompatActivity {

  private SectionsPagerAdapter mSectionsPagerAdapter;
  private ViewPager mViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_tabbed);
  }
}
