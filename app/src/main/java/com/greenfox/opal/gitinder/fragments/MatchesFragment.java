package com.greenfox.opal.gitinder.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.greenfox.opal.gitinder.GitinderApp;
import com.greenfox.opal.gitinder.model.response.ProfileListResponse;
import com.greenfox.opal.gitinder.service.ApiService;
import com.greenfox.opal.gitinder.service.ProfileAdapter;
import com.greenfox.opal.gitinder.R;
import com.greenfox.opal.gitinder.model.response.Profile;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchesFragment extends Fragment {

  private static final String TAG = "MatchesFragment";
  private static final String TOKEN = "Backend Response Token";

  @Inject
  ApiService service;
  @Inject
  SharedPreferences preferences;
  ProfileAdapter adapter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    Log.d(TAG, "on Matches tab");
    View rootView = inflater.inflate(R.layout.fragment_matches, container, false);
    ListView listView = (ListView) rootView.findViewById(R.id.matchList);

    GitinderApp.app().basicComponent().inject(this);

    adapter = new ProfileAdapter(getActivity(), new ArrayList<Profile>());

    listView.setAdapter(adapter);
    getMatchesList(preferences.getString(TOKEN, null), 0);
    return rootView;
  }

  private void getMatchesList(String token, int page) {
    service.getListOfTinders(token, page).enqueue(new Callback<ProfileListResponse>() {
      @Override
      public void onResponse(Call<ProfileListResponse> call,
                             Response<ProfileListResponse> response) {
        if (response.body().getStatus() != null) {
          Log.d("dev", response.body().getMessage());
        } else {
          List<Profile> members = response.body().getProfiles();
          for (Profile p : members) {
            Log.d("dev", p.getLogin() + ":" + p.getAvatarUrl() + ":" + p.getRepos() + ":" + p
                .getLanguages());
            adapter.addAll(p);
            adapter.notifyDataSetChanged();
          }
        }
      }

      @Override
      public void onFailure(Call<ProfileListResponse> call, Throwable t) {
        Log.d("dev", "FAIL! =(");
      }
    });
  }
}
