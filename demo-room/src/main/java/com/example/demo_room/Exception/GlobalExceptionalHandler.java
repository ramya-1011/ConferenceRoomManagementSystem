package com.example.demo_room.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionalHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleMyException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "Please check the details and try again.");

        Map<String, List<String>> modelState = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            modelState.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(message);
        });

        errorResponse.put("errorState", modelState);
        errorResponse.put("status", Boolean.FALSE);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
