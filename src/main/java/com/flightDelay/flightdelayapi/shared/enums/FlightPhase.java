package com.flightDelay.flightdelayapi.shared.enums;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeStage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum FlightPhase {
    DEPARTURE(List.of(AdditionalTimeStage.TAXI_OUT)),

    ARRIVAL(List.of(AdditionalTimeStage.TAXI_IN, AdditionalTimeStage.ASMA)),

    DEPARTURE_AND_ARRIVAL(List.of(AdditionalTimeStage.TAXI_OUT, AdditionalTimeStage.TAXI_IN, AdditionalTimeStage.ASMA));

    private final List<AdditionalTimeStage> stage;
}
