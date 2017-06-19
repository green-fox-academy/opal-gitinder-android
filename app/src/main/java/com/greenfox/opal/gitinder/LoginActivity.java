package com.greenfox.opal.gitinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.greenfox.opal.gitinder.service.ApiService;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {

  @Inject
  ObjectManager objectManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    MyApp.app().basicComponent().inject(this);
  }
}
