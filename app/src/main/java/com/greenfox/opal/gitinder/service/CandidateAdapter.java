package com.greenfox.opal.gitinder.service;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenfox.opal.gitinder.R;
import com.greenfox.opal.gitinder.model.response.Profile;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CandidateAdapter extends ArrayAdapter<Profile> {

  public CandidateAdapter(@NonNull Context context, List<Profile> list) {
    super(context, 0, list);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Profile current = getItem(position);
    convertView = LayoutInflater.from(getContext()).inflate(R.layout.candidate, parent, false);

    TextView username = (TextView)convertView.findViewById(R.id.textview_name);
    username.setText(current.getLogin());
    TextView languages = (TextView)convertView.findViewById(R.id.textview_language);
    languages.setText(current.getLanguages().toString());

    ImageView avatar = (ImageView) convertView.findViewById(R.id.imageView);
    Picasso.with(getContext())
        .load(current.getAvatarUrl())
        .into(avatar);
//    int id = getContext().getResources()
//        .getIdentifier(current.getAvatarUrl(), "drawable", getContext().getPackageName());
//    avatar.setImageResource(id);

    return convertView;
  }
}
