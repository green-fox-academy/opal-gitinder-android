package com.greenfox.opal.gitinder.model.response;

import com.greenfox.opal.gitinder.model.ExtendedMessage;

import java.util.List;

public class MessageResponse extends BaseResponse{
  private List<ExtendedMessage> messages;

  public MessageResponse() {
  }

  public MessageResponse(List<ExtendedMessage> messages) {
    this.messages = messages;
  }

  public MessageResponse(String message) {
    super("error", message);
  }

  public void setMessages(List<ExtendedMessage> messages) {

    this.messages = messages;
  }

  public List<ExtendedMessage> getMessages() {

    return messages;
  }
}
