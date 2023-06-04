package com.flightDelay.flightdelayapi.arrivalDelay;

import com.flightDelay.flightdelayapi.shared.dataImport.UpdateFromJson;

import java.util.List;

public interface ArrivalDelayService extends UpdateFromJson {

    boolean save(ArrivalDelay arrivalDelay);

    List<ArrivalDelayDto> findAllLatestByAirport(String airportIdent);
}
