package com.leadpilot.crm.exception;

/**
 * ==========================================================
 * Exception : ResourceNotFoundException
 *
 * Description :
 * Thrown when a requested resource cannot be found
 * in the database.
 * ==========================================================
 */

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}