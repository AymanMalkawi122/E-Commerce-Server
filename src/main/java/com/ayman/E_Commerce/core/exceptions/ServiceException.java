package com.ayman.E_Commerce.core.exceptions;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {
    public final HttpStatus ErrorCode;

    public ServiceException(String message, Throwable cause, HttpStatus errorCode) {
        super(message, cause);
        ErrorCode = errorCode;
    }
    public ServiceException(String message, Throwable cause) {
        this(message, cause, null);
    }
}
