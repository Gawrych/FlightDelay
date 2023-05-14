package com.flightDelay.flightdelayapi.shared.exception;

public class RequestValidationException extends CustomRuntimeException {

    public RequestValidationException(String message, Object[] parameters) {
        super(message, parameters);
    }
}
