package com.katruk.entity.dto;

import com.katruk.entity.User;

public final class UserDto {

  private Long userId;
  private String lastName;
  private String name;
  private String patronymic;
  private String username;
  private String password;
  private String confirmPassword;
  private User.Role role;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPatronymic() {
    return patronymic;
  }

  public void setPatronymic(String patronymic) {
    this.patronymic = patronymic;
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

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public User.Role getRole() {
    return role;
  }

  public void setRole(User.Role role) {
    this.role = role;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserDto{");
    sb.append("userId=").append(userId);
    sb.append(", lastName='").append(lastName).append('\'');
    sb.append(", name='").append(name).append('\'');
    sb.append(", patronymic='").append(patronymic).append('\'');
    sb.append(", username='").append(username).append('\'');
    sb.append(", password='").append(password).append('\'');
    sb.append(", confirmPassword='").append(confirmPassword).append('\'');
    sb.append(", role=").append(role);
    sb.append('}');
    return sb.toString();
  }
}
