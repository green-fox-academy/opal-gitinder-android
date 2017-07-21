package com.greenfox.opal.gitinder;

import static com.greenfox.opal.gitinder.LoginActivity.X_GITINDER_TOKEN;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.greenfox.opal.gitinder.model.ExtendedMessage;
import com.greenfox.opal.gitinder.model.Message;
import com.greenfox.opal.gitinder.model.response.MessageResponse;
import com.greenfox.opal.gitinder.model.response.PostMessageResponse;
import com.greenfox.opal.gitinder.service.ApiService;
import com.greenfox.opal.gitinder.service.MessageAdapter;

import java.util.ArrayList;
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

    GitinderApp.app().basicComponent().inject(this);

    buttonSend = (Button) findViewById(R.id.button_send);
    listMessages = (ListView) findViewById(R.id.message_list);
    messageEditText = (EditText) findViewById(R.id.message_edit_text);
    messageAdapter = new MessageAdapter(this, new ArrayList<Message>());
    final Bundle bundle = getIntent().getExtras();
    String value = "";
    if (bundle != null) {
      value = bundle.getString("username");
    }
    listAllMessages(preferences.getString(X_GITINDER_TOKEN, null), value);
    listMessages.setAdapter(messageAdapter);

    buttonSend.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Message message = new Message(bundle.getString("username"), messageEditText.getText().toString());
        sendMessage(preferences.getString(X_GITINDER_TOKEN, null), message);
      }
    });
  }

  public void listAllMessages(String token, String username) {
    service.getMessages(token, username).enqueue(new Callback<MessageResponse>() {
      @Override
      public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
        if (response.body().getStatus().equals("ok")) {
          List<ExtendedMessage> allMessages = response.body().getMessages();
          for (ExtendedMessage m : allMessages) {
            try {
              messageAdapter.add(m);
              messageAdapter.notifyDataSetChanged();
            } catch (Exception e) {
              Log.d("Dev", "FAIL");
            }
          }
        }
      }

      @Override
      public void onFailure(Call<MessageResponse> call, Throwable t) {
        Log.d("dev", "FAIL! =(");
      }
    });
  }

  public void sendMessage(String token, Message message) {
    service.postMessage(token, message).enqueue(new Callback<PostMessageResponse>() {
      @Override
      public void onResponse(Call<PostMessageResponse> call,
          Response<PostMessageResponse> response) {
        if (response.body().getStatus().equals("ok")) {
          messageAdapter.add(response.body().getNewMessage());
          messageAdapter.notifyDataSetChanged();
        } else {
          Log.d("dev", response.body().getMessage());
        }
      }

      @Override
      public void onFailure(Call<PostMessageResponse> call, Throwable t) {

      }
    });
  }
}
