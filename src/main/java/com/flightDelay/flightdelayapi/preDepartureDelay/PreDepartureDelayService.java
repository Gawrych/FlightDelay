package com.flightDelay.flightdelayapi.preDepartureDelay;

import com.flightDelay.flightdelayapi.shared.UpdateFromJson;

public interface PreDepartureDelayService extends UpdateFromJson {

    void save(PreDepartureDelay preDepartureDelay);
}
