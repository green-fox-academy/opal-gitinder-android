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
  public static final String SWITCHSTATE = "Switch State";
  public static final String NOTIFICATIONS = "Enable Notifications";
  public static final String BACKGROUNDSYNC = "Enable Background Sync";

  @Inject
  SharedPreferences preferences;
  SharedPreferences.Editor editor;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    Log.d(TAG, "on Setting tab");
    final View view = inflater.inflate(R.layout.fragment_settings, container, false);
    final Switch switchNotifications = (Switch) view.findViewById(R.id.switch_notifications);
    final Switch switchSync = (Switch) view.findViewById(R.id.switch_sync);
    editor = getActivity().getSharedPreferences(SWITCHSTATE, Context.MODE_PRIVATE).edit();

    switchNotifications.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if(switchNotifications.isChecked()) {
          editor.putBoolean(NOTIFICATIONS, true);
          editor.apply();
          Toast.makeText(getContext(), getString(R.string.settings_notifications_on), Toast.LENGTH_SHORT).show();
        } else {
          editor.putBoolean(NOTIFICATIONS, false);
          editor.apply();
          Toast.makeText(getContext(), getString(R.string.settings_notifications_off), Toast.LENGTH_SHORT).show();
        }
      }
    });

    switchSync.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if(switchSync.isChecked()) {
          editor.putBoolean(BACKGROUNDSYNC, true);
          editor.apply();
          Toast.makeText(getContext(), getString(R.string.settings_background_sync_on), Toast.LENGTH_SHORT).show();
        } else {
          editor.putBoolean(BACKGROUNDSYNC, false);
          editor.apply();
          Toast.makeText(getContext(), getString(R.string.settings_background_sync_off), Toast.LENGTH_SHORT).show();
        }
      }
    });

    preferences = getActivity().getSharedPreferences(SWITCHSTATE, Context.MODE_PRIVATE);
    switchNotifications.setChecked(preferences.getBoolean(NOTIFICATIONS, false));
    switchSync.setChecked(preferences.getBoolean(BACKGROUNDSYNC, false));
    return view;
  }
}
