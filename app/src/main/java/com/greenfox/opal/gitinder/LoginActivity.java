package com.greenfox.opal.gitinder;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
  Button loginButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    onButtonClickListener();
  }

  public void onButtonClickListener() {
    loginButton = (Button) findViewById(R.id.login_button);
    loginButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(LoginActivity.this);
        a_builder.setMessage(R.string.dialog_message)
            .setCancelable(false)
            .setPositiveButton(R.string.dialog_button_login,new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
              }
            })
            .setNegativeButton(R.string.dialog_button_exit,new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                finish();
              }
            }) ;
        AlertDialog alert = a_builder.create();
        alert.setTitle(R.string.dialog_title);
        alert.show();
      }
    });
  }
}
