package com.greenfox.opal.gitinder.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;

import com.greenfox.opal.gitinder.R;

/*
  Use to show this dialog:
    MatchDialogFragment dialog = new MatchDialogFragment();
    dialog.show(fragmentManager, "dialog");
 */

public class MatchDialogFragment extends DialogFragment {
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.match_dialog, container, false);
    Button buttonBack = (Button)view.findViewById(R.id.buttonBack);
    buttonBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });
    Button buttonChat = (Button)view.findViewById(R.id.buttonChat);
    buttonChat.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        TabHost host = (TabHost)getActivity().findViewById(R.id.tabs);
        host.setCurrentTab(1);  
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
