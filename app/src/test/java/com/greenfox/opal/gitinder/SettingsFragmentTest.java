package com.greenfox.opal.gitinder;

import static junit.framework.Assert.assertTrue;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Switch;
import com.greenfox.opal.gitinder.fragments.SettingsFragment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SettingsFragmentTest {

  SettingsFragment settingsFragment;
  SharedPreferences sharedPreferences;

  @Before
  public void setup() throws Exception {
    settingsFragment = new SettingsFragment();
    sharedPreferences = RuntimeEnvironment.application.getSharedPreferences("Switch State",
        Context.MODE_PRIVATE);
    startFragment(settingsFragment);
  }

  @Test
  public void saveNotificationSwitchStateInSharedPreferences() {
    Switch switchNotification = (Switch) settingsFragment.getView().findViewById(R.id.switch_notifications);

    switchNotification.performClick();
    assertTrue(sharedPreferences.contains("Enable Notifications"));
  }
}
