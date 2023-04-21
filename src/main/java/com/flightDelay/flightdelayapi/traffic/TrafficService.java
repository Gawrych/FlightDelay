package com.flightDelay.flightdelayapi.traffic;

import com.flightDelay.flightdelayapi.shared.UpdateFromJson;

public interface TrafficService extends UpdateFromJson {

    void save(Traffic traffic);
}
