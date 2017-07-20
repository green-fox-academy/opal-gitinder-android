package com.greenfox.opal.gitinder;

import static com.greenfox.opal.gitinder.LoginActivity.X_GITINDER_TOKEN;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.greenfox.opal.gitinder.model.ExtendedMessage;
import com.greenfox.opal.gitinder.model.response.MessageResponse;
import com.greenfox.opal.gitinder.service.ApiService;
import com.greenfox.opal.gitinder.service.MessageAdapter;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesActivity extends AppCompatActivity {

  @Inject
  ApiService service;
  @Inject
  SharedPreferences preferences;
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
    Bundle bundle = getIntent().getExtras();
    listAllMessages(preferences.getString(X_GITINDER_TOKEN, null), bundle.getString("username"));
    messageAdapter.addAll();
    listMessages.setAdapter(messageAdapter);
  }

  public void listAllMessages(String token, String username) {
    service.getMessages(token, username).enqueue(new Callback<MessageResponse>() {
      @Override
      public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
        if (response.body().getStatus().equals("ok")) {
          List<ExtendedMessage> allMessages = response.body().getMessages();
        }
      }

      @Override
      public void onFailure(Call<MessageResponse> call, Throwable t) {

      }
    });
  }
}
