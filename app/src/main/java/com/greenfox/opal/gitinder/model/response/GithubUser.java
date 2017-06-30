package com.greenfox.opal.gitinder.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Nagy Dóra on 2017.06.26..
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubUser extends BaseResponse{

  private String login;

  public GithubUser() {
  }

  public GithubUser(String login) {
    this.login = login;
  }

  public String getLogin() {
    return login;
  }
}
