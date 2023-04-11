package com.flightDelay.flightdelayapi.repository;

import com.flightDelay.flightdelayapi.model.Airport;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends ListCrudRepository<Airport, Long> {

    Airport findByAirportIdent(String airportIdent);
}
