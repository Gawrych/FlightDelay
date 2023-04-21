package com.flightDelay.flightdelayapi.departureAdditionalTime;

import com.flightDelay.flightdelayapi.shared.UpdateFromJson;

public interface DepartureAdditionalTimeService extends UpdateFromJson {

    void save(DepartureAdditionalTime departureAdditionalTime);
}
