package com.katruk.entity;

public final class Person extends Model {

  private final String lastName;
  private final String name;
  private final String patronymic;

  public Person(Long id) {
    super(id);
    this.lastName = null;
    this.name = null;
    this.patronymic = null;
  }

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

  public Person addId(Long id) {
    return new Person(id, this.lastName, this.name, this.patronymic);
  }
}