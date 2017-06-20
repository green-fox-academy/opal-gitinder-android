package com.greenfox.opal.gitinder.model;

import java.util.List;

/**
 * Created by Bálint on 2017. 06. 19..
 */

public class ProfileRequest {
  String login;
  String avatarUrl;
  List<String> repos;
  List<String> languages;

  public ProfileRequest() {
  }

  public String getLogin() {
    return login;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public List<String> getRepos() {
    return repos;
  }

  public List<String> getLanguages() {
    return languages;
  }
}
