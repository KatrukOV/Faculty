package com.katruk.exception;

public class DaoException extends Exception {

  public DaoException() {

  }

  public DaoException(String reason, Throwable cause) {
    super(reason, cause);
  }
}
