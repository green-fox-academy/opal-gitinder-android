package com.greenfox.opal.gitinder.model.response;

import com.greenfox.opal.gitinder.model.ExtendedMessage;
import com.greenfox.opal.gitinder.model.Message;

public class PostMessageResponse extends StatusResponse {

  private ExtendedMessage message;

  public PostMessageResponse() {
  }

  public PostMessageResponse(String message) {
    super(message);
  }

  public PostMessageResponse(ExtendedMessage message) {
    this.status = "ok";
    this.message = message;
  }

  public ExtendedMessage getMessage() {
    return message;
  }
}
