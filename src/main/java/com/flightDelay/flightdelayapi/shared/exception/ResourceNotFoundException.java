package com.flightDelay.flightdelayapi.shared.exception;

public class ResourceNotFoundException extends CustomRuntimeException {

    public ResourceNotFoundException(String message, Object[] parameters) {
        super(message, parameters);
    }
}
