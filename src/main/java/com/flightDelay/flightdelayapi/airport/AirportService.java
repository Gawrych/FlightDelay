package com.flightDelay.flightdelayapi.airport;

public interface AirportService {

    Airport findByAirportIdent(String airportIdent);

    void save(Airport airport);
}
