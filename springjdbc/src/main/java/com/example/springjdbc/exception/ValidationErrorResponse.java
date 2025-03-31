package com.example.springjdbc.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ValidationErrorResponse {
    private final int status;
    private final String error;
    private final String message;
    private final Map<String, String> errors;
    private final LocalDateTime timestamp = LocalDateTime.now();
}