package com.company.carpark.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PermissionException extends RuntimeException {

  private final HttpStatus httpStatus;

  public PermissionException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

}
