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

public class MessageAdapter extends ArrayAdapter<Message> {

  public MessageAdapter(Context context) {
    super(context, 0);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    Message current = getItem(position);

    if(convertView == null){
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.message, parent, false);
    }

    TextView text = (TextView) convertView.findViewById(R.id.text);
    text.setText(current.getText());

    return convertView;
  }
}
