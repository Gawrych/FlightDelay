package com.flightDelay.flightdelayapi.traffic;

import com.flightDelay.flightdelayapi.shared.dataImport.UpdateFromJson;

import java.util.List;

public interface TrafficService extends UpdateFromJson {

    boolean save(Traffic traffic);

    Traffic setAirportBidirectionalRelationshipByCode(String airportCode, Traffic traffic);

    List<TrafficDto> findAllLatestByAirport(String airportCode);
}
