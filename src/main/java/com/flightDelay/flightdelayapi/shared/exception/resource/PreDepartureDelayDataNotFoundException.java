package com.flightDelay.flightdelayapi.shared.exception.resource;

public class PreDepartureDelayDataNotFoundException extends ResourceNotFoundException {

    public PreDepartureDelayDataNotFoundException() {
        super("error.message.preDepartureDelayDataNotFound", new Object[]{});
    }
}
