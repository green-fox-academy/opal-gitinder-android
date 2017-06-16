package com.greenfox.opal.gitinder;

<<<<<<< HEAD
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.model.StatusResponse;

import org.junit.After;
import org.junit.Before;
=======
>>>>>>> dori-mainlayout
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import retrofit2.Call;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RobolectricTest {

    @Test
    public void wildButtonTest() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void loginTest() throws IOException {
        MockServer server = new MockServer();
        Call<StatusResponse> response = server.login(new LoginRequest("Bond", "abcd1234"));
        assertEquals(response.execute().body().getStatus(), "ok");
        assertEquals(response.execute().body().getToken(), "abc123");
    }

    @Test
    public void loginFailTest() throws IOException {
        MockServer server = new MockServer();
        Call<StatusResponse> response = server.login(new LoginRequest("NemBond", "1234abcd"));
        assertEquals(response.execute().body().getStatus(), "error");
        assertEquals(response.execute().body().getMessage(), "Missing parameter(s): username!");
    }
}
