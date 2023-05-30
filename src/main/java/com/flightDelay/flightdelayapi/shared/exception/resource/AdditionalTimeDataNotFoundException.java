package com.flightDelay.flightdelayapi.shared.exception.resource;

public class AdditionalTimeDataNotFoundException extends ResourceNotFoundException {

    public AdditionalTimeDataNotFoundException() {
        super("error.message.additionalTimeDataNotFound", new Object[]{});
    }
}
