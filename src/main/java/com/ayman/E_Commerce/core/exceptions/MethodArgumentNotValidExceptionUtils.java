package com.ayman.E_Commerce.core.exceptions;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.stream.Collectors;

public class MethodArgumentNotValidExceptionUtils {

    public static String simpleMessage(MethodArgumentNotValidException e) {

        return e.getFieldErrors()
                .stream()
                .map(MethodArgumentNotValidExceptionUtils::simpleMessage)
                .collect(
                        Collectors.joining(", ")
                );
    }

    public static String simpleMessage(FieldError e) {
        return "on " + e.getObjectName() + ": " + e.getField() + " " + e.getDefaultMessage();
    }
}
