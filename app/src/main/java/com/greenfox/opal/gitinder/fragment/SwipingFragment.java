package com.greenfox.opal.gitinder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greenfox.opal.gitinder.R;

/**
 * Created by Nagy Dóra on 2017.06.20..
 */

public class SwipingFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_swiping, container, false);
	}
}
