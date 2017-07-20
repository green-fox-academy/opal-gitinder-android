package com.greenfox.opal.gitinder.model.response;

public class BaseResponse extends StatusResponse{
  String message;

  public BaseResponse() {
  }

  public BaseResponse(String status, String message) {
    this.status = status;
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }
}
