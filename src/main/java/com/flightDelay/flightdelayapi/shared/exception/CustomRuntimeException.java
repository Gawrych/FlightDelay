package com.flightDelay.flightdelayapi.shared.exception;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException {

    private final Object[] parameters;

    private final String errorMessage;

    public CustomRuntimeException(String message, Object[] parameters) {
        super(message);
        this.errorMessage = message;
        this.parameters = parameters;
    }
}
