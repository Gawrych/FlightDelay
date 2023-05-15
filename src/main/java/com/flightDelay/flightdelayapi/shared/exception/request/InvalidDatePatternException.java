package com.flightDelay.flightdelayapi.shared.exception.request;

public class InvalidDatePatternException extends RequestValidationException {

    public InvalidDatePatternException(String correctPattern) {
        super("error.message.invalidDatePattern", new Object[]{correctPattern});
    }
}
