package com.katruk.entity;

import java.sql.Date;

public final class Period extends Model {

  private final Status status;
  private final Date date;

  public Period(Status status, Date date) {
    super();
    this.status = status;
    this.date = date;
  }

  public Period(Long id, Status status, Date date) {
    super(id);
    this.status = status;
    this.date = date;
  }

  public enum Status {
    DISTRIBUTION,
    LEARNING
  }

  public Status status() {
    return status;
  }

  public Date date() {
    return date;
  }
}


