package com.katruk.entity;

public final class User extends Model {

  private Person person;
  private String username;
  private String password;
  private Role role;

  public enum Role {
    STUDENT,
    TEACHER,
    ADMIN
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("User{");
    sb.append("person=").append(person);
    sb.append(", username='").append(username).append('\'');
    sb.append(", password='").append(password).append('\'');
    sb.append(", role=").append(role);
    sb.append('}');
    return sb.toString();
  }
}
