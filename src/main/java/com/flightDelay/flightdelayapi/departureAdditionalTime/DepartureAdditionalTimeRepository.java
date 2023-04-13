package com.flightDelay.flightdelayapi.departureAdditionalTime;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartureAdditionalTimeRepository extends ListCrudRepository<DepartureAdditionalTime, Long> {

    List<DepartureAdditionalTime> findAllByAirportCode(String airportCode);

    boolean existsByGeneratedId(String generatedId);
}
