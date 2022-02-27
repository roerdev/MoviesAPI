package com.roerdev.moviesapi.exceptions;

public class NotFoundException extends RuntimeException {
    private static final String DESCRIPTION = "No existe el registro solicitado";

    public NotFoundException(String message) {
        super(String.format("%s. %s", DESCRIPTION, message));
    }
}
