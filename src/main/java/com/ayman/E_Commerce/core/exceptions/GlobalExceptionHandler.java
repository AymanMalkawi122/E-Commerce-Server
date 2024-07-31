package com.ayman.E_Commerce.core.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException e) {
        ErrorResponse errorResponse = new ErrorResponse("SERVICE_ERROR", e.getMessage());
        return new ResponseEntity<>(errorResponse, e.ErrorCode);
    }

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<ErrorResponse> handleRepositoryException(RepositoryException e) {
        ErrorResponse errorResponse = new ErrorResponse("REPOSITORY_ERROR", e.getMessage());
        return new ResponseEntity<>(errorResponse, e.ErrorCode);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("ENTITY_NOT_FOUND_ERROR", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        ErrorResponse errorResponse = new ErrorResponse("CONSTRAINT_VIOLATION_ERROR", ConstraintViolationExceptionUtils.simpleMessage(e));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse("ARGUMENT_VALIDATION_ERROR", MethodArgumentNotValidExceptionUtils.simpleMessage(e));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException e) {
        ErrorResponse errorResponse = new ErrorResponse("SERVER_ERROR", e.getMessage());
        return new ResponseEntity<>(errorResponse, e.getStatusCode());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> general(Exception e) {
        e.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse("GENERAL_ERROR", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
