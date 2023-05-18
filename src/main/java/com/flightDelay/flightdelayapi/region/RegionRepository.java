package com.flightDelay.flightdelayapi.region;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface RegionRepository extends ListCrudRepository<Region, Long> {

    Optional<Region> findByIsoCode(String isoCode);

    boolean existsByIsoCode(String isoCode);
}
