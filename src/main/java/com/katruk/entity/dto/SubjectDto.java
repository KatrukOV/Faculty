package com.katruk.entity.dto;

import com.katruk.entity.Teacher;

public final class SubjectDto {

  private Long subjectId;
  private String lastName;
  private String name;
  private String patronymic;
  private Teacher.Position position;
  private String title;

  public Long getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(Long subjectId) {
    this.subjectId = subjectId;
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

  public Teacher.Position getPosition() {
    return position;
  }

  public void setPosition(Teacher.Position position) {
    this.position = position;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
