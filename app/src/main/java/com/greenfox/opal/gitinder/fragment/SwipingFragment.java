package com.greenfox.opal.gitinder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greenfox.opal.gitinder.CandidateAdapter;
import com.greenfox.opal.gitinder.GitinderApp;
import com.greenfox.opal.gitinder.R;
import com.greenfox.opal.gitinder.model.response.Profile;
import com.greenfox.opal.gitinder.model.response.ProfileListResponse;
import com.greenfox.opal.gitinder.service.ApiService;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwipingFragment extends Fragment {

  @Inject
  ApiService service;
  CandidateAdapter adapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.swiping_fragment, container, false);

    GitinderApp.app().basicComponent().inject(this);

    SwipeFlingAdapterView flingAdapterView = (SwipeFlingAdapterView) view.findViewById(R.id.swipeView);
    adapter = new CandidateAdapter(view.getContext(), new ArrayList<Profile>());
    flingAdapterView.setAdapter(adapter);
    flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
      @Override
      public void removeFirstObjectInAdapter() {
        Log.d("dev", "REMOVE FIRST");
        adapter.remove(adapter.getItem(0));
        adapter.notifyDataSetChanged();
      }

      @Override
      public void onLeftCardExit(Object o) {
        Log.d("dev", "LEFT");
      }

      @Override
      public void onRightCardExit(Object o) {
        Log.d("dev", "RIGHT");
      }

      @Override
      public void onAdapterAboutToEmpty(int i) {
        Log.d("dev", "EMPTY");
      }

      @Override
      public void onScroll(float v) {

      }
    });

    onListRequest("header", 0);

    return view;
  }

  public void onListRequest(String token, Integer page) {
    service.getListOfTinders(token, page).enqueue(new Callback<ProfileListResponse>() {
      @Override
      public void onResponse(Call<ProfileListResponse> call, Response<ProfileListResponse> response) {
        if (response.body().getStatus() != null) {
          Log.d("dev", response.body().getMessage());
        } else {
          List<Profile> members = response.body().getProfiles();
          for (Profile p : members) {
            Log.d("dev", p.getLogin() + ":" + p.getAvatarUrl() + ":" + p.getRepos() + ":" + p.getLanguages());
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
