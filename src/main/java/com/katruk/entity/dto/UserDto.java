package com.katruk.entity.dto;

public final class UserDto {

  private String lastName;
  private String name;
  private String patronymic;
  private String username;
  private String password;
  private String confirmPassword;


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

  @Override
  public String toString() {
    return "UserDto{" +
           "lastName='" + lastName + '\'' +
           ", name='" + name + '\'' +
           ", patronymic='" + patronymic + '\'' +
           ", username='" + username + '\'' +
           ", password='" + password + '\'' +
           ", confirmPassword='" + confirmPassword + '\'' +
           '}';
  }
}
