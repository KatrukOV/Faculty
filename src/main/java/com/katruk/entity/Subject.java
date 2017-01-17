package com.katruk.entity;

public final class Subject extends Model {

  private String title;
  private Teacher teacher;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  @Override
  public String toString() {
    return "Subject{" +
           " id='" + getId() + '\'' +
           "title='" + title + '\'' +
           ", teacher=" + teacher +
           '}';
  }
}
