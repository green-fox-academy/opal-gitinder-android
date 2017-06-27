package com.greenfox.opal.gitinder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.greenfox.opal.gitinder.ProfileAdapter;
import com.greenfox.opal.gitinder.R;
import com.greenfox.opal.gitinder.model.response.Profile;

import java.util.ArrayList;

public class MatchesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.matches_fragment, container, false);

        ProfileAdapter adapter = new ProfileAdapter(view.getContext());
        ListView listView = (ListView)view.findViewById(R.id.matchList);
        listView.setAdapter(adapter);

        ArrayList<String> repos = new ArrayList<>();
        repos.add("opal-gitinder-android");
        ArrayList<String> languages = new ArrayList<>();
        languages.add("Java");

        adapter.add(new Profile("Garlyle", "", repos, languages));
        adapter.add(new Profile("balintvecsey", "", repos, languages));
        adapter.add(new Profile("dorinagy", "", repos, languages));

        return view;
    }
}
