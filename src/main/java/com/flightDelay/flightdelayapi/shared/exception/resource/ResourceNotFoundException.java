package com.flightDelay.flightdelayapi.shared.exception.resource;

import com.flightDelay.flightdelayapi.shared.exception.CustomRuntimeException;

public class ResourceNotFoundException extends CustomRuntimeException {

    public ResourceNotFoundException(String message, Object[] parameters) {
        super(message, parameters);
    }
}
