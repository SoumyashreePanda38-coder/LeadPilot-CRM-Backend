package com.leadpilot.crm.exception;

/**
 * ==========================================================
 * Exception : BadRequestException
 *
 * Description :
 * Thrown when the client sends an invalid request
 * or business validation fails.
 * ==========================================================
 */

public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

}