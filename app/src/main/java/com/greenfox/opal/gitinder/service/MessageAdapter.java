package com.greenfox.opal.gitinder.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greenfox.opal.gitinder.R;
import com.greenfox.opal.gitinder.model.Message;

import java.util.ArrayList;

public class MessageAdapter extends ArrayAdapter<Message> {

  public MessageAdapter(Context context, ArrayList<Message> message) {
    super(context, 0, message);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    Message current = getItem(position);

    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.message, parent, false);
    }

    TextView message = (TextView) convertView.findViewById(R.id.message);
    message.setText(current.getMessage());

    return convertView;
  }
}
