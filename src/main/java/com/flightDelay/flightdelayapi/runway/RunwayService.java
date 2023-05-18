package com.flightDelay.flightdelayapi.runway;

import java.util.List;

public interface RunwayService {


    List<Runway> findByAirportIdent(String airportIdent);

    boolean existsById(Long id);

    List<Runway> updateFromJson(String newDataInJson);

    boolean save(Runway runway);
}
