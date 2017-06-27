package com.greenfox.opal.gitinder.model.response;

import java.sql.Timestamp;
import java.util.List;

public class Match {
    String username;
    Timestamp matched_at;
    List<String> messages;

    public String getUsername() {
        return username;
    }

    public Timestamp getMatched_at() {
        return matched_at;
    }

    public List<String> getMessages() {
        return messages;
    }
}
