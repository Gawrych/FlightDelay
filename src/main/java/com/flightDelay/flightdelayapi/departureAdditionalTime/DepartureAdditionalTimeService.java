package com.flightDelay.flightdelayapi.departureAdditionalTime;

import com.flightDelay.flightdelayapi.shared.dataImport.UpdateFromJson;

public interface DepartureAdditionalTimeService extends UpdateFromJson {

    void save(DepartureAdditionalTime departureAdditionalTime);
}
