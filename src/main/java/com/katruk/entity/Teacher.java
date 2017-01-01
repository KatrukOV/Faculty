package com.katruk.entity;

public final class Teacher extends Model {

  private User user;
  private Position position;

  public enum Position {
    PROFESSOR,
    ASSOCIATE_PROFESSOR,
    ASSISTANT
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }
}
