package com.greenfox.opal.gitinder;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        loginAlert();
      }
    }, 0);
  }

  public void loginAlert() {
    AlertDialog.Builder a_builder = new AlertDialog.Builder(LoginActivity.this);
    a_builder.setMessage(R.string.dialog_message)
        .setCancelable(false)
        .setPositiveButton(R.string.dialog_button_login, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            recreate(); //change after add oauth login
          }
        })
        .setNegativeButton(R.string.dialog_button_exit, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            finishAffinity();
          }
        });
    AlertDialog alert = a_builder.create();
    alert.setTitle(R.string.dialog_title);
    alert.show();
  }
}
