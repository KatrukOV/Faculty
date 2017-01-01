package com.katruk.entity;

import java.io.Serializable;

class Model implements Serializable {

  private long id;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
