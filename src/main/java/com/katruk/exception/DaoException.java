package com.katruk.exception;

public class DaoException extends Exception {

  public DaoException() {

  }

  public DaoException(final String reason, final Throwable cause) {
    super(reason, cause);
  }
}
