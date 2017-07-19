package com.greenfox.opal.gitinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.greenfox.opal.gitinder.service.MessageAdapter;

public class MessagesActivity extends AppCompatActivity {

  Button buttonSend;
  ListView listMessages;
  EditText messageEditText;
  MessageAdapter messageAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_messages);

    buttonSend = (Button) findViewById(R.id.button_send);
    listMessages = (ListView) findViewById(R.id.message_list);
    messageEditText = (EditText) findViewById(R.id.message_edit_text);
    messageAdapter = new MessageAdapter(this);
    listMessages.setAdapter(messageAdapter);
  }
}
