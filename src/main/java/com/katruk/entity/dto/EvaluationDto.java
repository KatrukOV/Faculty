package com.katruk.entity.dto;

import com.katruk.entity.Evaluation;

public final class EvaluationDto {

  private String lastName;
  private String name;
  private String patronymic;
  private Evaluation.Rating rating;
  private String feedback;

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
}
