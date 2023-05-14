package com.flightDelay.flightdelayapi.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiError {
    private final String path;

    private final String message;

    private final String debugMessage;

    private final HttpStatus httpStatus;

    private final int statusCode;

    private final LocalDateTime timestamp;

    public ApiError(String path, Throwable ex, HttpStatus httpStatus) {
        this.path = path;
        this.message = ex.getMessage();
        this.debugMessage = ex.getLocalizedMessage();
        this.httpStatus = httpStatus;
        this.statusCode = httpStatus.value();
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(String path, Throwable ex, String message, HttpStatus httpStatus) {
        this.path = path;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
        this.httpStatus = httpStatus;
        this.statusCode = httpStatus.value();
        this.timestamp = LocalDateTime.now();
    }
}
