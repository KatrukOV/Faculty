package com.katruk.entity;

public class Evaluation extends Model {

  private Subject subject;
  private Student student;
  private Status status;
  private Rating rating;
  private String feedback;


  public enum Status {
    DECLARED,
    CONFIRMED,
    REJECTED,
    DELETED
  }

  public enum Rating {
    A, B, C, D, E, Fx, F
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Rating getRating() {
    return rating;
  }

  public void setRating(Rating rating) {
    this.rating = rating;
  }

  public String getFeedback() {
    return feedback;
  }

  public void setFeedback(String feedback) {
    this.feedback = feedback;
  }
}