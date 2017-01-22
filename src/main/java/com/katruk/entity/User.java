package com.katruk.entity;

public final class User extends Model {

  private final Person person;
  private final String username;
  private final String password;
  private final Role role;

  public User(Long id) {
    super(id);
    this.person = null;
    this.username = null;
    this.password = null;
    this.role = null;
  }

  public User(Person person, String username, String password) {
    super();
    this.person = person;
    this.username = username;
    this.password = password;
    this.role = null;
  }

  public User(Person person, String username, String password, Role role) {
    super();
    this.person = person;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public User(Long id, Person person, String username, String password, Role role) {
    super(id);
    this.person = person;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public enum Role {
    STUDENT,
    TEACHER,
    ADMIN
  }

  public Person person() {
    return person;
  }

  public String username() {
    return username;
  }

  public String password() {
    return password;
  }

  public Role role() {
    return role;
  }
}