package com.flightDelay.flightdelayapi.preDepartureDelay;

import com.flightDelay.flightdelayapi.shared.dataImport.UpdateFromJson;

public interface PreDepartureDelayService extends UpdateFromJson {

    void save(PreDepartureDelay preDepartureDelay);

    PreDepartureDelay setAirportBidirectionalRelationshipByCode(String airportCode, PreDepartureDelay preDepartureDelay);
}
