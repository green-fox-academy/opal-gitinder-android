package com.greenfox.opal.gitinder.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.greenfox.opal.gitinder.GitinderApp;
import com.greenfox.opal.gitinder.model.response.Match;
import com.greenfox.opal.gitinder.model.response.MatchesResponse;
import com.greenfox.opal.gitinder.service.ApiService;
import com.greenfox.opal.gitinder.service.MatchesAdapter;
import com.greenfox.opal.gitinder.R;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchesFragment extends Fragment {

  @Inject
  ApiService service;
  @Inject
  SharedPreferences preferences;

  MatchesAdapter adapter;

  private static final String TAG = "MatchesFragment";

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    Log.d(TAG, "on Matches tab");
    View view = inflater.inflate(R.layout.fragment_matches, container, false);

    GitinderApp.app().basicComponent().inject(this);

    adapter = new MatchesAdapter(getActivity(), new ArrayList<Match>());

    ListView listView = (ListView) view.findViewById(R.id.matchList);
    listView.setAdapter(adapter);

    onMatchesRequest(preferences.getString("Backend Response Token", ""));
    return view;
  }

  public void onMatchesRequest(String token) {
    service.getMatches(token).enqueue(new Callback<MatchesResponse>() {
      @Override
      public void onResponse(Call<MatchesResponse> call, Response<MatchesResponse> response) {
        Log.d(TAG, "onResponse");
        if (response.body().getStatus() != null) {
          Log.d("dev", response.body().getMessage());
        } else {
          List<Match> members = response.body().getMatches();
          for (Match m : members) {
            Log.d("dev", m.getUsername() + ":" + m.getMatched_at() + ":" + m.getMessages());
            adapter.addAll(m);
            adapter.notifyDataSetChanged();
          }
        }
      }

      @Override
      public void onFailure(Call<MatchesResponse> call, Throwable t) {
        Log.d("dev", "FAIL! =(");
      }
    });
  }
}
