package com.greenfox.opal.gitinder;

public enum Direction {
	LEFT, RIGHT;

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
