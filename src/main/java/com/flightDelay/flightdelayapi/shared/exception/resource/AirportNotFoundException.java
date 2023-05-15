package com.flightDelay.flightdelayapi.shared.exception.resource;

public class AirportNotFoundException extends ResourceNotFoundException {

    public AirportNotFoundException(String airportIcaoCode) {
        super("error.message.airportNotFound", new Object[]{airportIcaoCode});
    }
}
