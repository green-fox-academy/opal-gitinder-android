package com.greenfox.opal.gitinder;

import com.greenfox.opal.gitinder.fragments.MatchesFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;

import static org.robolectric.util.FragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MatchesFragmentTest {

  private MatchesFragment matchesFragment;

  @Before
  public void setup() throws Exception {
    matchesFragment = new MatchesFragment();
    startFragment(matchesFragment);
  }

  @Test
  public void fragmentShouldNotBeNull() {
    assertNotNull(matchesFragment);
  }
}
