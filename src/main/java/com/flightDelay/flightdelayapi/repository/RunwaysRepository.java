package com.flightDelay.flightdelayapi.repository;

import com.flightDelay.flightdelayapi.model.Runway;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface RunwaysRepository extends ListCrudRepository<Runway, Long> {

    List<Runway> findAllByAirportCode(String airportCode);
}
