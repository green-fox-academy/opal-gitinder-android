package com.greenfox.opal.gitinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greenfox.opal.gitinder.model.response.Profile;

public class ProfileAdapter extends ArrayAdapter<Profile> {
    public ProfileAdapter(@NonNull Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Profile current = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.match, parent, false);
        TextView username = (TextView)convertView.findViewById(R.id.textName);
        username.setText(current.getLogin());
        TextView languages = (TextView)convertView.findViewById(R.id.textLanguages);
        languages.setText(current.getLanguages().toString());
//        ImageView avatar = (ImageView)convertView.findViewById(R.id.imageView);
//        avatar.setImageURI();

        return convertView;
    }
}
