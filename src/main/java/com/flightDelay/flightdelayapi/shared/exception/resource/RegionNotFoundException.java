package com.flightDelay.flightdelayapi.shared.exception.resource;

public class RegionNotFoundException extends ResourceNotFoundException {

    public RegionNotFoundException(String isoCode) {
        super("error.message.regionNotFound", new Object[]{isoCode});
    }
}
