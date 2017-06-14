package com.greenfox.opal.gitinder;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.greenfox.opal.gitinder.model.LoginRequest;
import com.greenfox.opal.gitinder.model.StatusResponse;
import com.greenfox.opal.gitinder.service.MockServer;

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
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        Button button = (Button)activity.findViewById(R.id.button);
        TextView result = (TextView)activity.findViewById(R.id.textView);
        EditText edit = (EditText)activity.findViewById(R.id.editText);

        button.performClick();
        assertEquals(result.getText().toString(), edit.getText().toString());
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
