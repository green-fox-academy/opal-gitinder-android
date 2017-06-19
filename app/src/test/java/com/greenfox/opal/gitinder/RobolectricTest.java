package com.greenfox.opal.gitinder;

import android.content.Intent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RobolectricTest {

  MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
  LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);

  @Test
  public void checkLoginNoUser() {
    mainActivity.checkLogin();

    Intent expectedIntent = new Intent(loginActivity, LoginActivity.class);
    assertEquals(expectedIntent.getClass(), shadowOf(mainActivity).getNextStartedActivity().getClass());
  }
}
