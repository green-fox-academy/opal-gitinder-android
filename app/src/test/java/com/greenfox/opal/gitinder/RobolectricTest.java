package com.greenfox.opal.gitinder;

import android.content.Intent;

import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, shadows = {ShadowViewPager.class})
public class RobolectricTest {

  private MainActivity mainActivity;
  private LoginActivity loginActivity;

  @Before
  public void setup() {
    mainActivity = Robolectric.setupActivity(MainActivity.class);
    loginActivity = Robolectric.setupActivity(LoginActivity.class);
  }

  @Test
  public void checkActivityNotNull() throws Exception {
    assertNotNull(mainActivity);
    assertNotNull(loginActivity);
  }

  @Test
  public void shouldHaveCorrectAppName() throws Exception {
    String appName = mainActivity.getResources().getString(R.string.app_name);
    assertEquals(appName, "GiTinder");
  }

  @Test
  public void checkLoginNoUser() throws Exception {
    Intent expectedIntent = new Intent(loginActivity, LoginActivity.class);
    assertEquals(expectedIntent.getClass(), shadowOf(mainActivity).getNextStartedActivity().getClass());
  }

  @Test
  public void saveStatusOnPause() {
    mainActivity.onPause();
    assertEquals(mainActivity.timestamp, mainActivity.preferences.getString(mainActivity.APP_STATE, ""));
  }
}
