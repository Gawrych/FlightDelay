package com.flightDelay.flightdelayapi.additionalTime;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdditionalTimeRepository extends ListCrudRepository<AdditionalTime, Long> {

    boolean existsByGeneratedId(String generatedId);

    @Query("SELECT u FROM AdditionalTime u WHERE u.airportCode = :airportCode AND u.date > :startDate")
    List<AdditionalTime> findAllByAirportWithDateAfter(String airportCode, LocalDate startDate);
}
