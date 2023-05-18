package com.flightDelay.flightdelayapi.region;

import java.util.List;

public interface RegionService {

    boolean save(Region region);

    boolean existsByIsoCode(String isoCode);

    Region findByIsoCode(String isoCode);

    List<Region> updateFromJson(String newDataInJson);
}
