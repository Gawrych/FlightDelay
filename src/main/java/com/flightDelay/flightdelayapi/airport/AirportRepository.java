package com.flightDelay.flightdelayapi.airport;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends ListCrudRepository<Airport, Long> {

    Optional<Airport> findByAirportIdent(String airportIdent);
}
