package com.flightDelay.flightdelayapi.repository;

import com.flightDelay.flightdelayapi.model.Region;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface RegionRepository extends ListCrudRepository<Region, Long> {

    List<Region> findAllByIsoCode(String isoCode);
}
