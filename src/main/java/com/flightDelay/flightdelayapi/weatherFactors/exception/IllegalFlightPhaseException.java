package com.flightDelay.flightdelayapi.weatherFactors.exception;

import com.flightDelay.flightdelayapi.shared.exception.LackOfCrucialDataException;

public class IllegalFlightPhaseException extends LackOfCrucialDataException {

    public IllegalFlightPhaseException(String flightPhase) {
        super("error.message.illegalFlightPhaseException", new Object[]{flightPhase});
    }
}
