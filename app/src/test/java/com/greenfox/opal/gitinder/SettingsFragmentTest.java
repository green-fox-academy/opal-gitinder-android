package com.greenfox.opal.gitinder;

import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

import com.greenfox.opal.gitinder.fragments.SettingsFragment;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SettingsFragmentTest {

  private SettingsFragment settingsFragment;

  @Before
  public void setup() throws Exception {
    settingsFragment = new SettingsFragment();
    startFragment(settingsFragment);
  }

}
