package com.flightDelay.flightdelayapi.traffic;

import com.flightDelay.flightdelayapi.shared.dataImport.UpdateFromJson;

public interface TrafficService extends UpdateFromJson {

    void save(Traffic traffic);
}
