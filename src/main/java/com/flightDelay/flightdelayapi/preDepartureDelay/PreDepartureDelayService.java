package com.flightDelay.flightdelayapi.preDepartureDelay;

import com.flightDelay.flightdelayapi.shared.dataImport.UpdateFromJson;

import java.util.List;

public interface PreDepartureDelayService extends UpdateFromJson {

    boolean save(PreDepartureDelay preDepartureDelay);

    List<PreDepartureDelayDto> findAllLatestByAirport(String airportIdent);

    PreDepartureDelay setAirportBidirectionalRelationshipByCode(String airportCode, PreDepartureDelay preDepartureDelay);

    List<PreDepartureDelay> updateFromJson(List<PreDepartureDelay> newDataInJson);
}
