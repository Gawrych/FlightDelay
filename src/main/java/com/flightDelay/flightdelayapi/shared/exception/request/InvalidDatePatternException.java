package com.flightDelay.flightdelayapi.shared.exception.request;

public class InvalidDatePatternException extends RequestValidationException {

    public InvalidDatePatternException(String correctPattern, String actualDate) {
        super("error.message.invalidDatePattern", new Object[]{correctPattern, actualDate});
    }
}
