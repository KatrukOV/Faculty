package com.katruk.exception;

public class ServiceException extends Exception {

  public ServiceException() {

  }

  public ServiceException(String reason, Throwable cause) {
    super(reason, cause);
  }
}
