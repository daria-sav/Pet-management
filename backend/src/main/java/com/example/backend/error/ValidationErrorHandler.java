package com.example.backend.error;

import java.util.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationErrorHandler {

    private Map<String, Object> errorBody(String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", message);
        body.put("errors", new LinkedHashMap<String, List<String>>());
        return body;
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> body = errorBody("Validation failed");
        Map<String, List<String>> errors = (Map<String, List<String>>) body.get("errors");

        ex.getBindingResult().getFieldErrors()
          .forEach(fe -> errors.computeIfAbsent(fe.getField(), k -> new ArrayList<>())
                               .add(fe.getDefaultMessage()));

        return body;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, Object> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> body = errorBody("Validation failed");
        Map<String, List<String>> errors = (Map<String, List<String>>) body.get("errors");

        for (ConstraintViolation<?> cv : ex.getConstraintViolations()) {
            String field = cv.getPropertyPath().toString();
            errors.computeIfAbsent(field, k -> new ArrayList<>())
                  .add(cv.getMessage());
        }

        return body;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, Object> handleDataIntegrity(DataIntegrityViolationException ex) {
        Map<String, Object> body = errorBody("Validation failed or constraint violation");
        Map<String, List<String>> errors = (Map<String, List<String>>) body.get("errors");

        String rootMsg = getRootMessage(ex);
        if (rootMsg != null && rootMsg.toLowerCase().contains("uk_pets_identification_number")) {
            errors.computeIfAbsent("identificationNumber", k -> new ArrayList<>())
                  .add("Identification number must be unique");
        }

        return body;
    }

    private String getRootMessage(Throwable t) {
        Throwable cur = t;
        while (cur.getCause() != null) cur = cur.getCause();
        return cur.getMessage();
    }
}