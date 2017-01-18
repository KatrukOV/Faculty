package com.katruk.entity;

import java.sql.Date;

public final class Period extends Model {

  private Status status;
  private Date date;

  public enum Status {
    DISTRIBUTION,
    LEARNING
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}


