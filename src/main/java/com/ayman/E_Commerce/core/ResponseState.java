package com.ayman.E_Commerce.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
public class ResponseState<T> {
    private T data;
    private String errorMessage;
    private HttpStatus responseCode;

    public ResponseState(String errorMessage, HttpStatus responseCode) {
        this(null, errorMessage, responseCode);
    }

    public ResponseState(T data, HttpStatus responseCode) {
        this(data, null, responseCode);
    }

    public ResponseEntity<T> toResponseEntity() {
        return new ResponseEntity<>(data, responseCode);
    }
}
