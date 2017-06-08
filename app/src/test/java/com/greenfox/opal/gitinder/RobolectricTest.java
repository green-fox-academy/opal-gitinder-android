package com.greenfox.opal.gitinder;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        Button button = (Button)activity.findViewById(R.id.button);
        TextView result = (TextView)activity.findViewById(R.id.textView);
        EditText edit = (EditText)activity.findViewById(R.id.editText);

        button.performClick();
        assertEquals(result.getText().toString(), edit.getText().toString());

    }
}
