package com.katruk.entity;

public final class Student extends Model {

  private User user;
  private Form form;
  private Contract contract;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Form getForm() {
    return form;
  }

  public void setForm(Form form) {
    this.form = form;
  }

  public Contract getContract() {
    return contract;
  }

  public void setContract(Contract contract) {
    this.contract = contract;
  }

  public enum Form {
    FULL_TIME,
    EXTRAMURAL,
    EVENING,
    DISTANCE
  }

  public enum Contract {
    STATE_ORDER,
    CONTRACT
  }
}
