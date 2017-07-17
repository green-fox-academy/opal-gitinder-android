package com.greenfox.opal.gitinder.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.greenfox.opal.gitinder.Direction;
import com.greenfox.opal.gitinder.GitinderApp;
import com.greenfox.opal.gitinder.LoginActivity;
import com.greenfox.opal.gitinder.R;
import com.greenfox.opal.gitinder.model.response.Profile;
import com.greenfox.opal.gitinder.model.response.ProfileListResponse;
import com.greenfox.opal.gitinder.model.response.SwipingResponse;
import com.greenfox.opal.gitinder.service.ApiService;
import com.greenfox.opal.gitinder.service.CandidateAdapter;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.VISIBLE;
import static com.greenfox.opal.gitinder.LoginActivity.X_GITINDER_TOKEN;

public class SwipingFragment extends Fragment {

  private static final String TAG = "SwipingFragment";

  @Inject
  ApiService service;
  @Inject
  SharedPreferences preferences;
  CandidateAdapter adapter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    Log.d(TAG, "on Swiping tab");
    final View view = inflater.inflate(R.layout.fragment_swiping, container, false);

    GitinderApp.app().basicComponent().inject(this);

    final SwipeFlingAdapterView flingAdapterView = (SwipeFlingAdapterView) view
      .findViewById(R.id.swipeView);
    adapter = new CandidateAdapter(view.getContext(), new ArrayList<Profile>());
    flingAdapterView.setAdapter(adapter);

    onListRequest(preferences.getString(X_GITINDER_TOKEN, null), 0);

    flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
      @Override
      public void removeFirstObjectInAdapter() {
        Log.d("dev", "REMOVE FIRST");
        adapter.remove(adapter.getItem(0));
        adapter.notifyDataSetChanged();
      }

      @Override
      public void onLeftCardExit(Object o) {
        Log.d("dev", Direction.LEFT.toString());
        Profile currentProfile = (Profile) o;
        onSwipingRequest(preferences.getString(X_GITINDER_TOKEN, null), currentProfile.getLogin(), Direction.LEFT);
      }

      @Override
      public void onRightCardExit(Object o) {
        Log.d("dev", Direction.RIGHT.toString());
        Profile currentProfile = (Profile) o;
        onSwipingRequest(preferences.getString(X_GITINDER_TOKEN, null), currentProfile.getLogin(), Direction.RIGHT);
      }

      @Override
      public void onAdapterAboutToEmpty(int i) {
        TextView text = (TextView) container.findViewById(R.id.noMoreProfiles);
        Log.d("dev", "EMPTY");

        if (i <= 3) {
          onListRequest(preferences.getString(X_GITINDER_TOKEN, null), 0);
        }
        if (i <= 0) {
          text.setVisibility(VISIBLE);
        }
      }

      @Override
      public void onScroll(float v) {

      }
    });

    Button buttonNope = (Button) view.findViewById(R.id.button_nope);
    buttonNope.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!adapter.isEmpty()) {
          Log.d("dev", Direction.LEFT.toString());
          onSwipingRequest(preferences.getString(X_GITINDER_TOKEN, null), adapter.getItem(0).getLogin(), Direction.LEFT);
          adapter.remove(adapter.getItem(0));
          adapter.notifyDataSetChanged();
          flingAdapterView.removeAllViewsInLayout();
        }
      }
    });

    Button buttonLike = (Button) view.findViewById(R.id.button_like);
    buttonLike.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!adapter.isEmpty()) {
          Log.d("dev", Direction.RIGHT.toString());
          onSwipingRequest(preferences.getString(X_GITINDER_TOKEN, null), adapter.getItem(0).getLogin(), Direction.RIGHT);
          adapter.remove(adapter.getItem(0));
          adapter.notifyDataSetChanged();
          flingAdapterView.removeAllViewsInLayout();
        }
      }
    });

    return view;
  }

  public void onListRequest(String token, Integer page) {
    final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading", "Please wait");
    service.getListOfTinders(token, page).enqueue(new Callback<ProfileListResponse>() {
      @Override
      public void onResponse(Call<ProfileListResponse> call, Response<ProfileListResponse> response) {
        dialog.dismiss();
        if (response.body().getStatus() != null) {
          Log.d("dev", response.body().getMessage());
        } else {
          List<Profile> members = response.body().getProfiles();
          for (Profile p : members) {
            Log.d("dev", p.getLogin() + ":" + p.getAvatarUrl() + ":" + p.getRepos() + ":" + p.getLanguages());
            adapter.add(p);
          }
        }
      }

      @Override
      public void onFailure(Call<ProfileListResponse> call, Throwable t) {
        Log.d("dev", "FAIL! =(");
      }
    });
  }

  public void onSwipingRequest(String token, String username, Direction direction) {
    service.swiping(token, username, direction).enqueue(new Callback<SwipingResponse>() {
      @Override
      public void onResponse(Call<SwipingResponse> call, Response<SwipingResponse> response) {
        if (response.body().getStatus() != null) {
          Log.d("dev", response.body().getMessage());
        } else {
          Log.d("dev", response.body().getMessage());
        }
      }

      @Override
      public void onFailure(Call<SwipingResponse> call, Throwable t) {
        Log.d("dev", "FAIL! =(");
      }
    });
  }
}
