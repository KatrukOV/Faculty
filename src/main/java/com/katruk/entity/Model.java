package com.katruk.entity;

import java.io.Serializable;

abstract class Model implements Serializable {

  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
