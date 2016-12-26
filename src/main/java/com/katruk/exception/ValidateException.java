package com.katruk.exception;

public class ValidateException extends Exception {

  public ValidateException() {

  }

  public ValidateException(String reason, Throwable cause) {
    super(reason, cause);
  }

  public ValidateException(String reason) {
    super(reason);
  }
}
