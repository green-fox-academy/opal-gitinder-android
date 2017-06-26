package com.greenfox.opal.gitinder;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greenfox.opal.gitinder.model.response.Profile;

import java.util.List;

public class CandidateAdapter extends ArrayAdapter<Profile> {
  public CandidateAdapter(@NonNull Context context, List<Profile> list) {
    super(context, 0, list);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Profile current = getItem(position);
    convertView = LayoutInflater.from(getContext()).inflate(R.layout.candidate, parent, false);
    TextView username = (TextView)convertView.findViewById(R.id.textName);
    username.setText(current.getLogin());
    TextView languages = (TextView)convertView.findViewById(R.id.textLanguages);
    languages.setText(current.getLanguages().toString());
//    ImageView avatar = (ImageView)convertView.findViewById(R.id.imageView);
//    avatar.setImageURI();

    return convertView;
  }
}
