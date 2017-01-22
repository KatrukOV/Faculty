package com.katruk.entity;

public final class Evaluation extends Model {

  private final Subject subject;
  private final Student student;
  private final Status status;
  private final Rating rating;
  private final String feedback;

  public Evaluation(Subject subject, Student student, Status status) {
    super();
    this.subject = subject;
    this.student = student;
    this.status = status;
    this.rating = null;
    this.feedback = null;
  }

  public Evaluation(Subject subject, Student student, Status status, Rating rating,
                    String feedback) {
    super();
    this.subject = subject;
    this.student = student;
    this.status = status;
    this.rating = rating;
    this.feedback = feedback;
  }

  public Evaluation(Long id, Subject subject, Student student, Status status, Rating rating,
                    String feedback) {
    super(id);
    this.subject = subject;
    this.student = student;
    this.status = status;
    this.rating = rating;
    this.feedback = feedback;
  }

  public enum Status {
    DECLARED,
    CONFIRMED,
    REJECTED,
    DELETED
  }

  public enum Rating {
    A, B, C, D, E, Fx, F
  }

  public Subject subject() {
    return subject;
  }

  public Student student() {
    return student;
  }

  public Status status() {
    return status;
  }

  public Rating rating() {
    return rating;
  }

  public String feedback() {
    return feedback;
  }
}