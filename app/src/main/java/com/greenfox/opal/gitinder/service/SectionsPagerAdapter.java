package com.greenfox.opal.gitinder.service;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

  private List<Fragment> mFragmentList;
  private List<String> mFragmentTitleList;

  public SectionsPagerAdapter(FragmentManager fm) {
    super(fm);
    mFragmentList = new ArrayList<>();
    mFragmentTitleList = new ArrayList<>();
  }

  public void addFragment(Fragment fragment, String title) {
    mFragmentList.add(fragment);
    mFragmentTitleList.add(title);
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return mFragmentTitleList.get(position);
  }

  @Override
  public Fragment getItem(int position) {
    return mFragmentList.get(position);
  }

  @Override
  public int getCount() {
    return mFragmentList.size();
  }
}
