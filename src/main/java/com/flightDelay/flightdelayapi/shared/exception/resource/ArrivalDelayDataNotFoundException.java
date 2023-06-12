package com.flightDelay.flightdelayapi.shared.exception.resource;

public class ArrivalDelayDataNotFoundException extends ResourceNotFoundException {

    public ArrivalDelayDataNotFoundException() {
        super("error.message.arrivalDelayDataNotFound", new Object[]{});
    }
}
