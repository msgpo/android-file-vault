package com.vandenbreemen.mobilesecurestorage.message;

/**
 * <h2>Intro</h2>
 * <p>Application error
 * <h2>Other Details</h2>
 *
 * @author kevin
 */
public class ApplicationError extends Exception {

    public ApplicationError(String message) {
        super(message);
    }

    public ApplicationError(String message, Throwable cause) {
        super(message, cause);
    }
}