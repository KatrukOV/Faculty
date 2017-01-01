package com.katruk.exception;

public class ServiceException extends Exception {

  public ServiceException() {

  }

  public ServiceException(final String reason, final Throwable cause) {
    super(reason, cause);
  }
}
