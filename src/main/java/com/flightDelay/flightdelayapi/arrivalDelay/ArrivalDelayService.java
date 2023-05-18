package com.flightDelay.flightdelayapi.arrivalDelay;

import com.flightDelay.flightdelayapi.shared.dataImport.UpdateFromJson;

public interface ArrivalDelayService extends UpdateFromJson {

    boolean save(ArrivalDelay arrivalDelay);
}
