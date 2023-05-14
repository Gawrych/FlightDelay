package com.flightDelay.flightdelayapi.shared.exception;

public class AirportNotFoundException extends ResourceNotFoundException {

    public AirportNotFoundException(String airportIcaoCode) {
        super("api.error.message.airportNotFound", new Object[]{airportIcaoCode});
    }
}
