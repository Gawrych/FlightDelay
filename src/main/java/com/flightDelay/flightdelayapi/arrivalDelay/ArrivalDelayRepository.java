package com.flightDelay.flightdelayapi.arrivalDelay;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArrivalDelayRepository extends ListCrudRepository<ArrivalDelay, Long> {

    boolean existsByGeneratedId(String generatedId);
}
