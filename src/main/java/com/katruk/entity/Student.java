package com.katruk.entity;

public final class Student extends Model {

  private final User user;
  private final Form form;
  private final Contract contract;

  public Student(User user) {
    super();
    this.user = user;
    this.form = null;
    this.contract = null;
  }

  public Student(User user, Form form) {
    super();
    this.user = user;
    this.form = form;
    this.contract = null;
  }

  public Student(User user, Contract contract) {
    super();
    this.user = user;
    this.form = null;
    this.contract = contract;
  }

  public Student(User user, Form form, Contract contract) {
    super();
    this.user = user;
    this.form = form;
    this.contract = contract;
  }

  public Student(Long id, User user, Form form, Contract contract) {
    super(id);
    this.user = user;
    this.form = form;
    this.contract = contract;
  }

  public enum Form {
    FULL_TAME,
    EXTRAMURAL,
    EVENING,
    DISTANCE
  }

  public enum Contract {
    STATE_ORDER,
    CONTRACT
  }

  public User user() {
    return user;
  }

  public Form form() {
    return form;
  }

  public Contract contract() {
    return contract;
  }
}
