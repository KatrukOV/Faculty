package com.katruk.entity;

public final class Person extends Model {

  private final String lastName;
  private final String name;
  private final String patronymic;

  public Person(String lastName, String name, String patronymic) {
    super();
    this.lastName = lastName;
    this.name = name;
    this.patronymic = patronymic;
  }

  public Person(Long id, String lastName, String name, String patronymic) {
    super(id);
    this.lastName = lastName;
    this.name = name;
    this.patronymic = patronymic;
  }

  public String lastName() {
    return lastName;
  }

  public String name() {
    return name;
  }

  public String patronymic() {
    return patronymic;
  }
}
