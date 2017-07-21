package com.greenfox.opal.gitinder.fragments;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.greenfox.opal.gitinder.GitinderApp;
import com.greenfox.opal.gitinder.R;

import javax.inject.Inject;

import static com.greenfox.opal.gitinder.LoginActivity.X_GITINDER_TOKEN;

public class MatchDialogFragment extends DialogFragment {
  @Inject
  SharedPreferences preferences;
  @Inject
  MatchesFragment fragment;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.match_dialog, container, false);
    GitinderApp.app().basicComponent().inject(this);
    fragment.onMatchesRequest(preferences.getString(X_GITINDER_TOKEN, null));
    Button buttonBack = (Button) view.findViewById(R.id.buttonBack);
    buttonBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });
    Button buttonChat = (Button) view.findViewById(R.id.buttonChat);
    buttonChat.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });
    return view;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Dialog dialog = super.onCreateDialog(savedInstanceState);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    return dialog;
  }
}
