package com.greenfox.opal.gitinder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import com.greenfox.opal.gitinder.R;

public class SettingsFragment extends Fragment {

  private static final String TAG = "SettingsFragment";

  Switch switchNotifications;
  Switch switchSync;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    Log.d(TAG, "on Setting tab");
    View view = inflater.inflate(R.layout.fragment_settings, container, false);

    switchNotifications = (Switch) view.findViewById(R.id.switch_notifications);
    switchSync = (Switch) view.findViewById(R.id.switch_sync);
    return view;
  }
}
