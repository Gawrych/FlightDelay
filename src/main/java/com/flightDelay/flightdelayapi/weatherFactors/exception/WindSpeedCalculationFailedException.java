package com.flightDelay.flightdelayapi.weatherFactors.exception;

import com.flightDelay.flightdelayapi.shared.exception.LackOfCrucialDataException;

public class WindSpeedCalculationFailedException extends LackOfCrucialDataException {

    public WindSpeedCalculationFailedException() {
        super("error.message.failedToCalculateWindSpeed", new Object[]{});
    }
}
