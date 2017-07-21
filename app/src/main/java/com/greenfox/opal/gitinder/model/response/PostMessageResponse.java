package com.greenfox.opal.gitinder.model.response;

import com.greenfox.opal.gitinder.model.ExtendedMessage;
import com.greenfox.opal.gitinder.model.Message;

public class PostMessageResponse extends BaseResponse {

  private ExtendedMessage newMessage;

  public PostMessageResponse() {
  }

  public PostMessageResponse(String message) {
    super("error", message);
  }

  public PostMessageResponse(ExtendedMessage newMessage) {
    this.status = "ok";
    this.newMessage = newMessage;
  }

  public ExtendedMessage getNewMessage() {
    return newMessage;
  }
}
