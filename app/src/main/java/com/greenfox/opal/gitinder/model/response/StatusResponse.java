package com.greenfox.opal.gitinder.model.response;

public class StatusResponse {
  String status;

  public StatusResponse() {

  }

  public StatusResponse(String status) {
    this.status = status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatus() {

    return status;
  }
}
