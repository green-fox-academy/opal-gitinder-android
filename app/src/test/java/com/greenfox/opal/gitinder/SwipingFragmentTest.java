package com.greenfox.opal.gitinder;

import android.widget.Button;

import com.greenfox.opal.gitinder.fragments.SwipingFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SwipingFragmentTest {
  SwipingFragment fragment;

  @Before
  public void setUp() throws Exception {
    fragment = new SwipingFragment();
    startFragment(fragment);
  }

  @Test
  public void checkIfCandidatesReceived() throws Exception {
    assertTrue(fragment.getListSize() > 0);
  }

  @Test
  public void testOKButton() throws Exception {
    int count = fragment.getListSize();
    Button button = (Button)fragment.getView().findViewById(R.id.button_like);
    button.performClick();
    assertTrue(fragment.getListSize() < count);
  }
}