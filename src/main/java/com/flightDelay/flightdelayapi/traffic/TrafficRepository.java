package com.flightDelay.flightdelayapi.traffic;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface TrafficRepository extends ListCrudRepository<Traffic, Long> {
    List<Traffic> findAllByAirportCode(String airportCode);

    boolean existsByGeneratedId(String generatedId);

    @Query("SELECT u FROM Traffic u WHERE u.airportCode = :airportCode AND u.date > :startDate")
    List<Traffic> findAllByAirportWithDateAfter(String airportCode, LocalDate startDate);
}
