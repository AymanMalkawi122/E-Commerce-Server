package com.ayman.E_Commerce.core.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;

public class ConstraintViolationExceptionUtils {

    public static String simpleMessage(ConstraintViolationException e) {
        return  e.getConstraintViolations()
                .stream()
                .map(ConstraintViolationExceptionUtils::simpleMessage)
                .collect(Collectors.joining(", "));
    }

    public static String simpleMessage(ConstraintViolation<?> violation) {
        return "on " + violation.getRootBeanClass().getSimpleName() + ": " + violation.getPropertyPath() + " " + violation.getMessage();
    }
}
