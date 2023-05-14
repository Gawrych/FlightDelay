package com.flightDelay.flightdelayapi.shared.exception;

public class InvalidDatePatternException extends RequestValidationException {

    public InvalidDatePatternException(String correctPattern) {
        super("api.error.message.invalidDatePattern", new Object[]{correctPattern});
    }
}
