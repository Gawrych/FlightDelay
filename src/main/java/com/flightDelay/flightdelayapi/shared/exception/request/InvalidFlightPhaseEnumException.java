package com.flightDelay.flightdelayapi.shared.exception.request;

public class InvalidFlightPhaseEnumException extends RequestValidationException {

    public InvalidFlightPhaseEnumException(String phaseName) {
        super("error.message.invalidPhase", new Object[]{phaseName});
    }
}
