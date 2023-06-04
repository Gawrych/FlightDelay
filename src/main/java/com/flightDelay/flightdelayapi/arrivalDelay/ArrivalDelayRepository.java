package com.flightDelay.flightdelayapi.arrivalDelay;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ArrivalDelayRepository extends ListCrudRepository<ArrivalDelay, Long> {

    boolean existsByGeneratedId(String generatedId);

    @Query("SELECT u FROM ArrivalDelay u WHERE u.airportCode = :airportIdent AND u.date > :startDate")
    List<ArrivalDelay> findAllByAirportWithDateAfter(String airportIdent, LocalDate startDate);
}
