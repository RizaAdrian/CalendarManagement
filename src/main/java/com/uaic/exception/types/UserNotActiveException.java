package com.uaic.exception.types;

@SuppressWarnings("serial")
public class UserNotActiveException extends RuntimeException {

  public UserNotActiveException(String msg, Throwable t) {
    super(msg, t);
  }

  public UserNotActiveException(String msg) {
    super(msg);
  }
}
