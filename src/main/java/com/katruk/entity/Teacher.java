package com.katruk.entity;

public final class Teacher extends Model {

  private final User user;
  private final Position position;

  public Teacher(User user) {
    super();
    this.user = user;
    this.position = null;
  }

  public Teacher(User user, Position position) {
    super();
    this.user = user;
    this.position = position;
  }

  public Teacher(Long id, User user, Position position) {
    super(id);
    this.user = user;
    this.position = position;
  }

  public enum Position {
    PROFESSOR,
    ASSOCIATE_PROFESSOR,
    ASSISTANT
  }

  public User user() {
    return user;
  }

  public Position position() {
    return position;
  }
}