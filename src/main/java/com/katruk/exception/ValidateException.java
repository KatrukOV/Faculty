package com.katruk.exception;

public class ValidateException extends Exception {

  public ValidateException() {

  }

  public ValidateException(final String reason, final Throwable cause) {
    super(reason, cause);
  }

  public ValidateException(final String reason) {
    super(reason);
  }
}
