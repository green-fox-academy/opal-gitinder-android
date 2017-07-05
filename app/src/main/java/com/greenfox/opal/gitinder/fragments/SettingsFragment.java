package com.greenfox.opal.gitinder.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;
import com.greenfox.opal.gitinder.R;
import javax.inject.Inject;

public class SettingsFragment extends Fragment {

  private static final String TAG = "SettingsFragment";

  Switch switchNotifications;
  Switch switchSync;
  @Inject
  SharedPreferences preferences;
  SharedPreferences.Editor editor;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    Log.d(TAG, "on Setting tab");
    final View view = inflater.inflate(R.layout.fragment_settings, container, false);
    editor = getActivity().getSharedPreferences("SwitchState", Context.MODE_PRIVATE).edit();

    switchNotifications = (Switch) view.findViewById(R.id.switch_notifications);
    switchNotifications.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if(switchNotifications.isChecked()) {
          editor.putBoolean("SwitchNotifications", true);
          editor.apply();
          Toast.makeText(getContext(), "Notifications On", Toast.LENGTH_SHORT).show();
        } else {
          editor.putBoolean("SwitchNotifications", false);
          editor.apply();
          Toast.makeText(getContext(), "Notifications Off", Toast.LENGTH_SHORT).show();
        }
      }
    });

    switchSync = (Switch) view.findViewById(R.id.switch_sync);
    switchSync.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if(switchSync.isChecked()) {
          editor.putBoolean("SwitchSync", true);
          editor.apply();
          Toast.makeText(getContext(), "Background Sync On", Toast.LENGTH_SHORT).show();
        } else {
          editor.putBoolean("SwitchSync", false);
          editor.apply();
          Toast.makeText(getContext(), "Background Sync Off", Toast.LENGTH_SHORT).show();
        }
      }
    });

    preferences = getActivity().getSharedPreferences("SwitchState", Context.MODE_PRIVATE);
    switchNotifications.setChecked(preferences.getBoolean("SwitchNotifications", false));
    switchSync.setChecked(preferences.getBoolean("SwitchSync", false));
    return view;
  }
}
