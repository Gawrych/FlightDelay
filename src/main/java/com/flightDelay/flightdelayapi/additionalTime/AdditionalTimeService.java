package com.flightDelay.flightdelayapi.additionalTime;

import com.flightDelay.flightdelayapi.shared.dataImport.UpdateFromJson;

import java.util.List;

public interface AdditionalTimeService extends UpdateFromJson {

    boolean save(AdditionalTime additionalTime);

    List<AdditionalTimeDto> findAllLatestByAirport(String airportIdent);

    List<AdditionalTime> updateFromJson(List<AdditionalTime> additionalTimes);
}
