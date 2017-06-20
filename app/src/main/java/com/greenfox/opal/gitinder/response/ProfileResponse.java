package com.greenfox.opal.gitinder.response;

import com.greenfox.opal.gitinder.model.Profile;

/**
 * Created by Bálint on 2017. 06. 20..
 */

public class ProfileResponse extends BaseResponse {
  private Profile profile;

  public ProfileResponse(String message) {
    super("error", message);
  }

  public ProfileResponse(Profile profile) {
    this.profile = profile;
  }

  public Profile getProfile() {
    return profile;
  }
}
