package com.flightDelay.flightdelayapi.arrivalDelay;

import com.flightDelay.flightdelayapi.shared.UpdateFromJson;
import org.springframework.http.ResponseEntity;

public interface ArrivalDelayService extends UpdateFromJson {

    void save(ArrivalDelay arrivalDelay);
}
