package com.greenfox.opal.gitinder.model;

import java.sql.Timestamp;

public class Message {

  private long id;
  private String from;
  private String to;
  private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
  private String message;

  public Message() {
  }

  public String getMessage() {
    return message;
  }
}
