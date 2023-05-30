package com.flightDelay.flightdelayapi.preDepartureDelay;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PreDepartureDelayRepository extends ListCrudRepository<PreDepartureDelay, Long> {

    List<PreDepartureDelay> findAllByAirportCode(String airportCode);

    boolean existsByGeneratedId(String generatedId);

    boolean existsByAirportCode(String airportIdent);

    @Query("SELECT u FROM PreDepartureDelay u WHERE u.airportCode = :airportIdent AND u.flightDate > :startDate")
    List<PreDepartureDelay> findAllByAirportWithDateAfter(String airportIdent, LocalDate startDate);
}
