package com.flightDelay.flightdelayapi.airport;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends ListCrudRepository<Airport, Long> {

    Airport findByAirportIdent(String airportIdent);
}
