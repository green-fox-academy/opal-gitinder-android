package com.greenfox.opal.gitinder;

import android.content.SharedPreferences;

import com.google.gson.Gson;

public class ObjectManager {
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public ObjectManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public ObjectManager(SharedPreferences sharedPreferences, Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    public void save(Object o, String key){
        sharedPreferences.edit()
                .putString(key, gson.toJson(o))
                .apply();
    }

    public <T> T get(String key, Class<T> type){
        String json = sharedPreferences.getString(key, null);
        if(json == null){
            return null;
        }

        return gson.fromJson(json, type);
    }
}
