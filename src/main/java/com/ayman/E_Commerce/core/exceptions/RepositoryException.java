package com.ayman.E_Commerce.core.exceptions;

import org.springframework.http.HttpStatus;

public class RepositoryException extends RuntimeException {
    public final HttpStatus ErrorCode;

    public RepositoryException(String message, Throwable cause, HttpStatus errorCode) {
        super(message, cause);
        ErrorCode = errorCode;
    }

    public RepositoryException(String message, HttpStatus errorCode) {
        this(message, null, errorCode);
    }
}
