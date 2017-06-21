package com.greenfox.opal.gitinder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.greenfox.opal.gitinder.R;

/**
 * Created by Bálint on 2017. 06. 21..
 */

public class SwipingFragment extends Fragment {

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.settings_fragment, container, false);
    return view;
  }
}
