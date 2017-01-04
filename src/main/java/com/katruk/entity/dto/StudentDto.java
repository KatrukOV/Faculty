package com.katruk.entity.dto;

import com.katruk.entity.Student;

public final class StudentDto {

  private Long studentId;
  private String lastName;
  private String name;
  private String patronymic;
  private Student.Form form;
  private Student.Contract contract;

  public Long getStudentId() {
    return studentId;
  }

  public void setStudentId(Long studentId) {
    this.studentId = studentId;
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

  public Student.Form getForm() {
    return form;
  }

  public void setForm(Student.Form form) {
    this.form = form;
  }

  public Student.Contract getContract() {
    return contract;
  }

  public void setContract(Student.Contract contract) {
    this.contract = contract;
  }
}
