package com.flightDelay.flightdelayapi.shared.exception;

public class InvalidFlightPhaseEnumException extends RequestValidationException {

    public InvalidFlightPhaseEnumException(String phaseName) {
        super("api.error.message.invalidPhase", new Object[]{phaseName});
    }
}
