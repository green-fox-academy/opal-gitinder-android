package com.greenfox.opal.gitinder.model.response;

import java.util.List;

/**
 * Created by Bálint on 2017. 07. 05..
 */

public class Settings extends BaseResponse {

  private boolean enableNotifications;
  private boolean enableBackgroundSync;
  private int maxDistance;
  private List<String> preferredLanguages;

  public Settings() {
  }

  public Settings(String message) {
    super("error", message);
  }

  public Settings(boolean enableNotifications, boolean enableBackgroundSync, int maxDistance,
      List<String> preferredLanguages) {
    this.enableNotifications = enableNotifications;
    this.enableBackgroundSync = enableBackgroundSync;
    this.maxDistance = maxDistance;
    this.preferredLanguages = preferredLanguages;
  }

  public boolean isEnableNotifications() {
    return enableNotifications;
  }

  public boolean isEnableBackgroundSync() {
    return enableBackgroundSync;
  }

  public int getMaxDistance() {
    return maxDistance;
  }

  public List<String> getPreferredLanguages() {
    return preferredLanguages;
  }
}
