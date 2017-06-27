package com.greenfox.opal.gitinder.model.response;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Match {
    String username;
    Timestamp matched_at;
    ArrayList<String> messages;

    public Match(String username, Timestamp time) {
        this.username = username;
        matched_at = time;
        messages = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public Timestamp getMatched_at() {
        return matched_at;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }
}
