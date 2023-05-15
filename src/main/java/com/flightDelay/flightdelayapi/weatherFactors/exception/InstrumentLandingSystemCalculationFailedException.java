package com.flightDelay.flightdelayapi.weatherFactors.exception;

import com.flightDelay.flightdelayapi.shared.exception.LackOfCrucialDataException;

public class InstrumentLandingSystemCalculationFailedException extends LackOfCrucialDataException {

    public InstrumentLandingSystemCalculationFailedException() {
        super("error.message.failedToCalculateInstrumentLandingSystem", new Object[]{});
    }
}
