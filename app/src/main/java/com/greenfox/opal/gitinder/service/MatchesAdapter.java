package com.greenfox.opal.gitinder.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greenfox.opal.gitinder.R;
import com.greenfox.opal.gitinder.model.response.Match;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchesAdapter extends ArrayAdapter<Match> {

  public MatchesAdapter(@NonNull Context context, ArrayList<Match> matches) {
    super(context, 0, matches);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Match current = getItem(position);

    TextView username = (TextView) convertView.findViewById(R.id.textName);
    username.setText(current.getUsername());

    TextView latestMessage = (TextView) convertView.findViewById(R.id.textMessage);
    latestMessage.setText(current.getLatestMessage());

    CircleImageView avatar = (CircleImageView) convertView.findViewById(R.id.imageView);
    int id = getContext().getResources()
      .getIdentifier(current.getAvatarUrl(), "drawable", getContext().getPackageName());
    avatar.setImageResource(id);

    return convertView;
  }
}
