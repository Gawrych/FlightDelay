package com.flightDelay.flightdelayapi.departureAdditionalTime;

import com.flightDelay.flightdelayapi.shared.dataImport.UpdateFromJson;

public interface DepartureAdditionalTimeService extends UpdateFromJson {

    boolean save(DepartureAdditionalTime departureAdditionalTime);

    DepartureAdditionalTime setAirportBidirectionalRelationshipByCode(String airportCode, DepartureAdditionalTime departureAdditionalTime);
}
