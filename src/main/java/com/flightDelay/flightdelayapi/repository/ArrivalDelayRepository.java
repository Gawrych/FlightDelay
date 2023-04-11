package com.flightDelay.flightdelayapi.repository;

import com.flightDelay.flightdelayapi.model.ArrivalDelay;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArrivalDelayRepository extends ListCrudRepository<ArrivalDelay, Long> {
    @Query("SELECT d FROM ArrivalDelay d WHERE d.flightArrivalDelay > :number")
    List<ArrivalDelay> findByFlightArrivalDelay(@Param("number") int number);
    List<ArrivalDelay> findAllByAirportCode(String airportCode);

    boolean existsByGeneratedId(String generatedId);

}
