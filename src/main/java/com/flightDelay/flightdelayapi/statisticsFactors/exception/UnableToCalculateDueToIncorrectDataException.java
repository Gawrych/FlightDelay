package com.flightDelay.flightdelayapi.statisticsFactors.exception;

import com.flightDelay.flightdelayapi.shared.exception.LackOfCrucialDataException;

public class UnableToCalculateDueToIncorrectDataException extends LackOfCrucialDataException {

    public UnableToCalculateDueToIncorrectDataException() {
        super("error.message.unableToCalculateDueToIncorrectDataException", new Object[]{});
    }
}
