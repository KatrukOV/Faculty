package com.katruk.entity.dto;

import com.katruk.entity.Evaluation;

public final class EvaluationDto {

  private Long evaluationId;
  private String title;
  private String lastName;
  private String name;
  private String patronymic;
  private Evaluation.Status status;
  private Evaluation.Rating rating;
  private String feedback;

  public Long getEvaluationId() {
    return evaluationId;
  }

  public void setEvaluationId(Long evaluationId) {
    this.evaluationId = evaluationId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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

  public Evaluation.Status getStatus() {
    return status;
  }

  public void setStatus(Evaluation.Status status) {
    this.status = status;
  }

  public Evaluation.Rating getRating() {
    return rating;
  }

  public void setRating(Evaluation.Rating rating) {
    this.rating = rating;
  }

  public String getFeedback() {
    return feedback;
  }

  public void setFeedback(String feedback) {
    this.feedback = feedback;
  }

  @Override
  public String toString() {
    return "EvaluationDto{" +
           "title='" + title + '\'' +
           ", evaluationId=" + evaluationId +
           ", lastName='" + lastName + '\'' +
           ", name='" + name + '\'' +
           ", patronymic='" + patronymic + '\'' +
           ", status=" + status +
           ", rating=" + rating +
           ", feedback='" + feedback + '\'' +
           '}';
  }
}
