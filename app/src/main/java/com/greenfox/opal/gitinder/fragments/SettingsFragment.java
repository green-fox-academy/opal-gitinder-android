package com.greenfox.opal.gitinder.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.greenfox.opal.gitinder.R;
import com.greenfox.opal.gitinder.model.response.Profile;
import com.greenfox.opal.gitinder.service.ApiService;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_SHORT;
import static com.greenfox.opal.gitinder.LoginActivity.X_GITINDER_TOKEN;

public class SettingsFragment extends Fragment {

  private static final String TAG = "SettingsFragment";
  public static final String SWITCHSTATE = "Switch State";
  public static final String NOTIFICATIONS = "Enable Notifications";
  public static final String BACKGROUNDSYNC = "Enable Background Sync";

  @Inject
  SharedPreferences preferences;
  SharedPreferences.Editor editor;

  @Inject
  ApiService service;

  private String avatarUrl;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    Log.d(TAG, "on Setting tab");
    final View view = inflater.inflate(R.layout.fragment_settings, container, false);
    final Switch switchNotifications = (Switch) view.findViewById(R.id.switch_notifications);
    final Switch switchSync = (Switch) view.findViewById(R.id.switch_sync);
    editor = getActivity().getSharedPreferences(SWITCHSTATE, MODE_PRIVATE).edit();

    ImageView avatar = (ImageView) view.findViewById(R.id.profile_pic);
    Picasso.with(getContext())
      .load(onProfileInfoRequest(preferences.getString(X_GITINDER_TOKEN, null)))
      .into(avatar);

    switchNotifications.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (switchNotifications.isChecked()) {
          editor.putBoolean(NOTIFICATIONS, true);
          editor.apply();
          Toast.makeText(getContext(), getString(R.string.settings_notifications_on), LENGTH_SHORT).show();
        } else {
          editor.putBoolean(NOTIFICATIONS, false);
          editor.apply();
          Toast.makeText(getContext(), getString(R.string.settings_notifications_off), LENGTH_SHORT).show();
        }
      }
    });

    switchSync.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (switchSync.isChecked()) {
          editor.putBoolean(BACKGROUNDSYNC, true);
          editor.apply();
          Toast.makeText(getContext(), getString(R.string.settings_background_sync_on), LENGTH_SHORT).show();
        } else {
          editor.putBoolean(BACKGROUNDSYNC, false);
          editor.apply();
          Toast.makeText(getContext(), getString(R.string.settings_background_sync_off), LENGTH_SHORT).show();
        }
      }
    });

    preferences = getActivity().getSharedPreferences(SWITCHSTATE, MODE_PRIVATE);
    switchNotifications.setChecked(preferences.getBoolean(NOTIFICATIONS, false));
    switchSync.setChecked(preferences.getBoolean(BACKGROUNDSYNC, false));
    return view;
  }

  public String onProfileInfoRequest(String token) {
    service.getProfileInfos(token).enqueue(new Callback<Profile>() {
      @Override
      public void onResponse(Call<Profile> call, Response<Profile> response) {
        Log.d(TAG, "onResponse");
        if (response.body().getStatus() != null) {
          Log.d("dev", response.body().getMessage());
        } else {
          avatarUrl = response.body().getAvatarUrl();
          Log.d("dev", response.body().getStatus());
        }
      }

      @Override
      public void onFailure(Call<Profile> call, Throwable t) {
        Log.d("dev", "FAIL! =(");
      }
    });
    return avatarUrl;
  }
}
