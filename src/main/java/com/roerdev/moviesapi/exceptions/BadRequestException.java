package com.roerdev.moviesapi.exceptions;

public class BadRequestException extends RuntimeException {
  private static final long serialVersionUID = -367545608214467341L;
  private static final String DESCRIPTION = "Bad Request Exception (400)";

  public BadRequestException(String mensaje) {
    super(DESCRIPTION + ". " + mensaje);
  }
}
