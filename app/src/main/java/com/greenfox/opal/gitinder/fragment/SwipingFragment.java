package com.greenfox.opal.gitinder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greenfox.opal.gitinder.ApiService;
import com.greenfox.opal.gitinder.CandidateAdapter;
import com.greenfox.opal.gitinder.R;
import com.greenfox.opal.gitinder.model.response.Profile;
import com.greenfox.opal.gitinder.model.response.ProfileListResponse;
import com.greenfox.opal.gitinder.service.MockServer;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SwipingFragment extends Fragment {

  ApiService service;
  Retrofit retrofit;
  boolean connectToBackend = false;
  CandidateAdapter adapter;
  ArrayList<Profile> list = new ArrayList<>();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.swiping_fragment, container, false);

    // Create Service
    if (connectToBackend) {
      retrofit = new Retrofit.Builder()
        .baseUrl("http://gitinder.herokuapp.com/")
        .addConverterFactory(JacksonConverterFactory.create())
        .build();
      service = retrofit.create(ApiService.class);
    } else {
      service = new MockServer();
    }

    SwipeFlingAdapterView flingAdapterView = (SwipeFlingAdapterView) view.findViewById(R.id.swipeView);
    adapter = new CandidateAdapter(view.getContext(), list);
    flingAdapterView.setAdapter(adapter);
    flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
      @Override
      public void removeFirstObjectInAdapter() {
        Log.d("dev", "REMOVE FIRST");
        list.remove(0);
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
            list.add(p);
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
