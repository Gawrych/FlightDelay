package com.flightDelay.flightdelayapi.weatherFactors.exception;

import com.flightDelay.flightdelayapi.shared.exception.LackOfCrucialDataException;

public class InstrumentLandingSystemCalculationFailedException extends LackOfCrucialDataException {

    public InstrumentLandingSystemCalculationFailedException() {
        super("api.error.message.failedToCalculateInstrumentLandingSystem", new Object[]{});
    }
}
