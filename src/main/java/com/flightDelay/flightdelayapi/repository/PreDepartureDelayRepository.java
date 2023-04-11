package com.flightDelay.flightdelayapi.repository;

import com.flightDelay.flightdelayapi.model.PreDepartureDelay;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreDepartureDelayRepository extends ListCrudRepository<PreDepartureDelay, Long> {

    List<PreDepartureDelay> findAllByAirportCode(String airportCode);

    boolean existsByGeneratedId(String generatedId);
}
