package com.katruk.entity;

public final class Subject extends Model {

  private final String title;
  private final Teacher teacher;

  public Subject(String title, Teacher teacher) {
    super();
    this.title = title;
    this.teacher = teacher;
  }

  public Subject(Long id, String title, Teacher teacher) {
    super(id);
    this.title = title;
    this.teacher = teacher;
  }

  public String title() {
    return title;
  }

  public Teacher teacher() {
    return teacher;
  }
}