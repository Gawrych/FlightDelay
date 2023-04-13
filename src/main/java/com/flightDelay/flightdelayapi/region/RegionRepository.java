package com.flightDelay.flightdelayapi.region;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface RegionRepository extends ListCrudRepository<Region, Long> {

    List<Region> findAllByIsoCode(String isoCode);
}
