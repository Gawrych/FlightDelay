package com.flightDelay.flightdelayapi.additionalTime;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdditionalTimeRepository extends ListCrudRepository<AdditionalTime, Long> {

    boolean existsByGeneratedId(String generatedId);

    @Query("SELECT u FROM AdditionalTime u WHERE u.airportCode = :airportIdent AND u.flightDate > :startDate AND u.stage != 'TAXI_OUT'")
    List<AdditionalTime> findAllArrivalByAirportWithDateAfter(String airportIdent, LocalDate startDate);

    @Query("SELECT u FROM AdditionalTime u WHERE u.airportCode = :airportIdent AND u.flightDate > :startDate AND u.stage = 'TAXI_OUT'")
    List<AdditionalTime> findAllDepartureByAirportWithDateAfter(String airportIdent, LocalDate startDate);

    @Query("SELECT u FROM AdditionalTime u WHERE u.airportCode = :airportIdent AND u.flightDate > :startDate")
    List<AdditionalTime> findAllByAirportWithDateAfter(String airportIdent, LocalDate startDate);
}
