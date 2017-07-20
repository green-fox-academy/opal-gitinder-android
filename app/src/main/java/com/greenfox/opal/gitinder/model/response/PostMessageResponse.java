package com.greenfox.opal.gitinder.model.response;

import com.greenfox.opal.gitinder.model.Message;

public class PostMessageResponse extends BaseResponse {

  private Message message;

  public PostMessageResponse() {
  }

  public PostMessageResponse(String message) {
    super("error", message);
  }

  public PostMessageResponse(Message message) {
    this.status = "ok";
    this.message = message;
  }
}
