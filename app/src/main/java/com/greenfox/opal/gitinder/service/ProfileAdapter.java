package com.greenfox.opal.gitinder.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greenfox.opal.gitinder.R;
import com.greenfox.opal.gitinder.model.response.Profile;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;

public class ProfileAdapter extends ArrayAdapter<Profile> {

  public ProfileAdapter(@NonNull Context context, ArrayList<Profile> profiles) {
    super(context, 0, profiles);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Profile current = getItem(position);
    convertView = LayoutInflater.from(getContext()).inflate(R.layout.match, parent, false);

    TextView username = (TextView) convertView.findViewById(R.id.textName);
    username.setText(current.getLogin());

    TextView languages = (TextView) convertView.findViewById(R.id.textLanguages);
    languages.setText(current.getLanguages().toString());


    CircleImageView avatar = (CircleImageView) convertView.findViewById(R.id.imageView);
    Picasso.with(getContext())
        .load(current.getAvatarUrl())
        .into(avatar);
//    int id = getContext().getResources()
//        .getIdentifier(current.getAvatarUrl(), "drawable", getContext().getPackageName());
//    avatar.setImageResource(id);

    return convertView;
  }
}
