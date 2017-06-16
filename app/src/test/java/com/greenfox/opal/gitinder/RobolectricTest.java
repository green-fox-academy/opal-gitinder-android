package com.greenfox.opal.gitinder;

<<<<<<< HEAD
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

<<<<<<< HEAD
import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.model.StatusResponse;

import org.junit.After;
import org.junit.Before;
=======
>>>>>>> dori-mainlayout
=======
>>>>>>> 71d579f8dae10845abd68d9ac0c8c62ecda23ba7
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RobolectricTest {

    @Test
    public void wildButtonTest() throws Exception {
        assertEquals(4, 2 + 2);
    }
}
