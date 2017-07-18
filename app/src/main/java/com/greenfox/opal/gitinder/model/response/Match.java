package com.greenfox.opal.gitinder.model.response;

import java.util.ArrayList;

public class Match {
  String username;
  String avatar_url;
  long matched_at;
  ArrayList<String> messages;

  public Match() {
  }

  public Match(String username, String avatar_url, long time, ArrayList<String> messages) {
    this.username = username;
    this.avatar_url = avatar_url;
    matched_at = time;
    this.messages = messages;
  }

  public String getUsername() {
    return username;
  }

  public long getMatched_at() {
    return matched_at;
  }

  public String getAvatarUrl() {
    return avatar_url;
  }

  public ArrayList<String> getMessages() {
    return messages;
  }

  public String getLatestMessage() {
    return getMessages().get(0);
  }
}
