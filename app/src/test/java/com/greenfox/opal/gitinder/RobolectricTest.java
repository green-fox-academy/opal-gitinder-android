package com.greenfox.opal.gitinder;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
//@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml", application = android.app.Application.class)
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

  //testing Toast
  @Test
  public void shouldHaveShortDuration() throws Exception {
    Toast toast = Toast.makeText(RuntimeEnvironment.application, "short toast", Toast.LENGTH_SHORT);
    assertNotNull(toast);
    assertEquals(toast.getDuration(), Toast.LENGTH_SHORT);
  }

  @Test
  public void shouldMakeTextCorrectly() throws Exception {
    Toast toast = Toast.makeText(RuntimeEnvironment.application, "login error", Toast.LENGTH_SHORT);
    assertNotNull(toast);
    assertEquals(toast.getDuration(), Toast.LENGTH_SHORT);
    toast.show();
    assertEquals(ShadowToast.getLatestToast(), toast);
    assertEquals(ShadowToast.getTextOfLatestToast(), "login error");
    assertTrue(ShadowToast.showedToast("login error"));
  }

  @Test
  public void shouldSetTextCorrectly() throws Exception {
    Toast toast = Toast.makeText(RuntimeEnvironment.application, "login error", Toast.LENGTH_SHORT);
    toast.setText("login error");
    toast.show();
    assertEquals(ShadowToast.getLatestToast(), toast);
    assertEquals(ShadowToast.getTextOfLatestToast(), "login error");
    assertTrue(ShadowToast.showedToast("login error"));
  }

  @Test
  public void shouldSetTextWithIdCorrectly() throws Exception {
    Toast toast = Toast.makeText(RuntimeEnvironment.application, "login error", Toast.LENGTH_SHORT);
    toast.setText(R.string.login_error);
    toast.show();
    assertEquals(ShadowToast.getLatestToast(), toast);
    assertEquals(ShadowToast.getTextOfLatestToast(), "login error");
    assertTrue(ShadowToast.showedToast("login error"));
  }

  @Test
  public void shouldSetViewCorrectly() throws Exception {
    Toast toast = new Toast(RuntimeEnvironment.application);
    toast.setDuration(Toast.LENGTH_SHORT);
    final View view = new TextView(RuntimeEnvironment.application);
    toast.setView(view);
    assertEquals(toast.getView(), view);
  }
}
