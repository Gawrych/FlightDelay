package com.flightDelay.flightdelayapi.runway;

import java.util.List;

public interface RunwayService {

    void deleteAll();

    List<Runway> findByAirportIdent(String airportIdent);
}
