package com.flightDelay.flightdelayapi.arrivalDelay;

import com.flightDelay.flightdelayapi.shared.dataImport.UpdateFromJson;

public interface ArrivalDelayService extends UpdateFromJson {

    void save(ArrivalDelay arrivalDelay);

    ArrivalDelay setAirportBidirectionalRelationshipByCode(String airportCode, ArrivalDelay arrivalDelay);
}
