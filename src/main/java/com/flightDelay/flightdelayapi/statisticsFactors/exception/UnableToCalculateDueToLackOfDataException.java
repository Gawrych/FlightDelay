package com.flightDelay.flightdelayapi.statisticsFactors.exception;

import com.flightDelay.flightdelayapi.shared.exception.LackOfCrucialDataException;

public class UnableToCalculateDueToLackOfDataException extends LackOfCrucialDataException {

    public UnableToCalculateDueToLackOfDataException() {
        super("error.message.unableToCalculateDueToLackOfDataException", new Object[]{});
    }
}
