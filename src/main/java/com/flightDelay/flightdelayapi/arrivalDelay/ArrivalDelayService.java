package com.flightDelay.flightdelayapi.arrivalDelay;

import com.flightDelay.flightdelayapi.shared.dataImport.UpdateFromJson;

public interface ArrivalDelayService extends UpdateFromJson {

    void save(ArrivalDelay arrivalDelay);
}
