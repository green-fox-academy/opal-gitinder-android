package com.greenfox.opal.gitinder.model;

import java.sql.Timestamp;

public class ExtendedMessage extends Message {

  private long id;
  private String from;
  private long created_at;

  public ExtendedMessage() {
  }

  public ExtendedMessage(String to, String message, long id, String from, long created_at) {
    super(to, message);
    this.id = id;
    this.from = from;
    this.created_at = created_at;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public void setCreated_at(long created_at) {
    this.created_at = created_at;
  }

  public long getId() {

    return id;
  }

  public String getFrom() {
    return from;
  }

  public long getCreated_at() {
    return created_at;
  }
}
