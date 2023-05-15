package com.flightDelay.flightdelayapi.shared.exception;


public class LackOfCrucialDataException extends CustomRuntimeException {

    public LackOfCrucialDataException(String message, Object[] parameters) {
        super(message, parameters);
    }
}
