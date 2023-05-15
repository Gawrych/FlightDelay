package com.flightDelay.flightdelayapi.shared.exception.request;

import com.flightDelay.flightdelayapi.shared.exception.CustomRuntimeException;

public class RequestValidationException extends CustomRuntimeException {

    public RequestValidationException(String message, Object[] parameters) {
        super(message, parameters);
    }
}
