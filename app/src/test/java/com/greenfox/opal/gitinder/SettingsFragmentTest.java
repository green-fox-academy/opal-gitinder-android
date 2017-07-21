package com.greenfox.opal.gitinder;

import static com.greenfox.opal.gitinder.fragments.SettingsFragment.BACKGROUNDSYNC;
import static com.greenfox.opal.gitinder.fragments.SettingsFragment.NOTIFICATIONS;
import static com.greenfox.opal.gitinder.fragments.SettingsFragment.SWITCHSTATE;
import static junit.framework.Assert.assertFalse;
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
    sharedPreferences = RuntimeEnvironment.application.getSharedPreferences(SWITCHSTATE,
        Context.MODE_PRIVATE);
    startFragment(settingsFragment);
  }

  @Test
  public void defaultNotificationSwitchState() {
    Switch switchNotification = (Switch) settingsFragment.getView().findViewById(R.id.switch_notifications);
    assertFalse(switchNotification.isChecked());
  }

  @Test
  public void saveNotificationSwitchStateInSharedPreferences() {
    Switch switchNotification = (Switch) settingsFragment.getView().findViewById(R.id.switch_notifications);
    switchNotification.performClick();
    assertTrue(switchNotification.isChecked());
    assertTrue(sharedPreferences.contains(NOTIFICATIONS));
  }

  @Test
  public void secondClickOnNotificationSwitchStateInSharedPreferences() {
    Switch switchNotification = (Switch) settingsFragment.getView().findViewById(R.id.switch_notifications);
    switchNotification.performLongClick();
    assertFalse(switchNotification.isChecked());
    assertFalse(sharedPreferences.contains(NOTIFICATIONS));
  }

  @Test
  public void defaultBackgroundSyncSwitchState() {
    Switch switchBackgroundSync = (Switch) settingsFragment.getView().findViewById(R.id.switch_sync);
    assertFalse(switchBackgroundSync.isChecked());
  }

  @Test
  public void saveBackgroundSyncSwitchStateInSharedPreferences() {
    Switch switchBackgroundSync = (Switch) settingsFragment.getView().findViewById(R.id.switch_sync);
    switchBackgroundSync.performClick();
    assertTrue(switchBackgroundSync.isChecked());
    assertTrue(sharedPreferences.contains(BACKGROUNDSYNC));
  }

  @Test
  public void secondClickOnBackgroundSyncSwitchStateInSharedPreferences() {
    Switch switchBackgroundSync = (Switch) settingsFragment.getView().findViewById(R.id.switch_sync);
    switchBackgroundSync.performLongClick();
    assertFalse(switchBackgroundSync.isChecked());
    assertFalse(sharedPreferences.contains(BACKGROUNDSYNC));
  }
}
