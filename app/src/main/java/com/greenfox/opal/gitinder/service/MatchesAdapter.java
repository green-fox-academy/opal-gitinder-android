package com.greenfox.opal.gitinder.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.greenfox.opal.gitinder.MainActivity;
import com.greenfox.opal.gitinder.MessagesActivity;
import com.greenfox.opal.gitinder.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import com.greenfox.opal.gitinder.model.response.Match;

import java.util.ArrayList;

public class MatchesAdapter extends ArrayAdapter<Match> {

  public MatchesAdapter(@NonNull Context context, ArrayList<Match> matches) {
    super(context, 0, matches);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final Match current = getItem(position);
    convertView = LayoutInflater.from(getContext()).inflate(R.layout.match, parent, false);

    final TextView username = (TextView) convertView.findViewById(R.id.textName);
    username.setText(current.getUsername());

    TextView latestMessage = (TextView) convertView.findViewById(R.id.textMessage);
    latestMessage.setText(current.getLatestMessage());

    Button messagesButton = (Button) convertView.findViewById(R.id.buttonMessages);
    messagesButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getContext(), MessagesActivity.class);
        intent.putExtra("username", current.getUsername());
        getContext().startActivity(intent);
      }
    });

    CircleImageView avatar = (CircleImageView) convertView.findViewById(R.id.imageView);
    Picasso.with(getContext())
        .load(current.getAvatarUrl())
        .into(avatar);

    return convertView;
  }
}
