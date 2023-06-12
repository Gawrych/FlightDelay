package com.flightDelay.flightdelayapi.shared.exception.resource;

public class TrafficDataNotFoundException extends ResourceNotFoundException {

    public TrafficDataNotFoundException() {
        super("error.message.trafficDataNotFound", new Object[]{});
    }
}
