package com.flightDelay.flightdelayapi.preDepartureDelay;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreDepartureDelayRepository extends ListCrudRepository<PreDepartureDelay, Long> {

    List<PreDepartureDelay> findAllByAirportCode(String airportCode);

    boolean existsByGeneratedId(String generatedId);
}
