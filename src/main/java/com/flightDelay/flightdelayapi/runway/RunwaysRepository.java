package com.flightDelay.flightdelayapi.runway;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface RunwaysRepository extends ListCrudRepository<Runway, Long> {

    List<Runway> findAllByAirportCode(String airportCode);
}
