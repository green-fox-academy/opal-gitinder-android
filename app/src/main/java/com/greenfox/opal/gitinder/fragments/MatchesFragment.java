package com.greenfox.opal.gitinder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.greenfox.opal.gitinder.R;

public class MatchesFragment extends Fragment {

  private static final String TAG = "MatchesFragment";

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    Log.d(TAG, "on Matches tab");
    return inflater.inflate(R.layout.fragment_matches, container, false);
  }
}
