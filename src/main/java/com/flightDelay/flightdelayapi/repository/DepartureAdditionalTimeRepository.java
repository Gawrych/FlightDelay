package com.flightDelay.flightdelayapi.repository;

import com.flightDelay.flightdelayapi.model.DepartureAdditionalTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartureAdditionalTimeRepository extends ListCrudRepository<DepartureAdditionalTime, Long> {

    List<DepartureAdditionalTime> findAllByAirportCode(String airportCode);

    @Query("SELECT d FROM DepartureAdditionalTime d WHERE d.stage = :stage ORDER BY d.flightDate DESC")
    List<DepartureAdditionalTime> findNewestRecordByStage(@Param("stage") String stage);

    boolean existsByGeneratedId(String generatedId);
}
