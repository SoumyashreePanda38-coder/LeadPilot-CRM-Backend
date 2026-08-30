package com.leadpilot.crm.exception;

/**
 * ==========================================================
 * Exception : InvalidCredentialsException
 *
 * Description :
 * Thrown when the username or password is incorrect
 * during authentication.
 * ==========================================================
 */

public class InvalidCredentialsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidCredentialsException() {
        super();
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

}