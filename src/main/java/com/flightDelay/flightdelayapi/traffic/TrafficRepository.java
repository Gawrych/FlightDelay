package com.flightDelay.flightdelayapi.traffic;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface TrafficRepository extends ListCrudRepository<Traffic, Long> {
    List<Traffic> findAllByAirportCode(String airportCode);

    boolean existsByGeneratedId(String generatedId);
}
