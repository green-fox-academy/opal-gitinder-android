package com.greenfox.opal.gitinder.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.greenfox.opal.gitinder.GitinderApp;
import com.greenfox.opal.gitinder.model.response.Match;
import com.greenfox.opal.gitinder.model.response.MatchesResponse;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.greenfox.opal.gitinder.LoginActivity.X_GITINDER_TOKEN;

public class MatchesBroadcast extends BroadcastReceiver {
  @Inject
  ApiService service;
  @Inject
  SharedPreferences preferences;

  @Override
  public void onReceive(Context context, Intent intent) {
    GitinderApp.app().basicComponent().inject(this);
    service.getMatches(preferences.getString(X_GITINDER_TOKEN, null)).enqueue(new Callback<MatchesResponse>() {
      @Override
      public void onResponse(Call<MatchesResponse> call, Response<MatchesResponse> response) {
        List<Match> matches = response.body().getMatches();
      }

      @Override
      public void onFailure(Call<MatchesResponse> call, Throwable t) {

      }
    });
  }
}
