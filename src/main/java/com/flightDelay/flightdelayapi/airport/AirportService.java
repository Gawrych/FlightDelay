package com.flightDelay.flightdelayapi.airport;

import java.util.List;

public interface AirportService {

    Airport findByAirportIdent(String airportIdent);

    boolean save(Airport airport);

    boolean existsByAirportIdent(String airportIdent);

    List<Airport> updateFromJson(String newDataInJsonString);
}
