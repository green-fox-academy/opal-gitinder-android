package com.greenfox.opal.gitinder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RobolectricTest {
    @Test
    public void wildButtonTest() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

    }
}
