package com.flightDelay.flightdelayapi.additionalTime;

import com.flightDelay.flightdelayapi.shared.dataImport.UpdateFromJson;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;

import java.util.List;

public interface AdditionalTimeService extends UpdateFromJson {

    boolean save(AdditionalTime additionalTime);

    AdditionalTime setAirportBidirectionalRelationshipByCode(String airportCode, AdditionalTime additionalTime);

    List<AdditionalTimeDto> findAllLatestByAirport(String airportIdent, FlightPhase phase);
}
