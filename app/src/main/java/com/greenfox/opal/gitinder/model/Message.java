package com.greenfox.opal.gitinder.model;

public class Message {

  private String to;
  private String message;

  public Message() {

  }

  public Message(String to, String message) {

    this.to = to;
    this.message = message;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getTo() {

    return to;
  }

  public String getMessage() {
    return message;
  }
}
