package com.katruk.entity;

import java.io.Serializable;

abstract class Model implements Serializable {

  private final Long id;

  Model() {
    this.id = null;
  }

  Model(Long id) {
    this.id = id;
  }
}
