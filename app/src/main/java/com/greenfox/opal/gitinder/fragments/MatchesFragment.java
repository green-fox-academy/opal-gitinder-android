package com.greenfox.opal.gitinder.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.greenfox.opal.gitinder.service.ProfileAdapter;
import com.greenfox.opal.gitinder.R;
import com.greenfox.opal.gitinder.model.response.Profile;
import java.util.ArrayList;

public class MatchesFragment extends Fragment {

  private static final String TAG = "MatchesFragment";

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    Log.d(TAG, "on Matches tab");
    View rootView = inflater.inflate(R.layout.fragment_matches, container, false);
    ListView listView = (ListView) rootView.findViewById(R.id.matchList);
    ArrayList<Profile> profiles = exampleMatchesList();

    listView.setAdapter(new ProfileAdapter(getActivity(), profiles));
    return rootView;
  }

  private ArrayList<Profile> exampleMatchesList() {
    ArrayList<String> repos = new ArrayList<>();
    ArrayList<String> languages = new ArrayList<>();
    ArrayList<Profile> profiles = new ArrayList<>();

    repos.add("opal-gitinder-android");
    languages.add("Java");
    profiles.add(new Profile("Garlyle", "thinker", repos, languages));
    profiles.add(new Profile("balintvecsey", "creepy", repos, languages));
    profiles.add(new Profile("dorinagy", "hungry", repos, languages));
    return profiles;
  }
}
