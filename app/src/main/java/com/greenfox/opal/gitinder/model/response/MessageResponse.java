package com.greenfox.opal.gitinder.model.response;

import com.greenfox.opal.gitinder.model.Message;

import java.util.List;

public class MessageResponse extends BaseResponse{
  private List<Message> messages;

  public MessageResponse() {
  }

  public MessageResponse(List<Message> messages) {
    this.messages = messages;
  }

  public void setMessages(List<Message> messages) {

    this.messages = messages;
  }

  public List<Message> getMessages() {

    return messages;
  }
}
