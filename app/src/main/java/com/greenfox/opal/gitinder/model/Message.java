package com.greenfox.opal.gitinder.model;

public class Message {

  private long id;
  private String from;
  private String to;
  private long createdAt;
  private String message;

  public Message() {
  }

  public String getMessage() {
    return message;
  }
}
