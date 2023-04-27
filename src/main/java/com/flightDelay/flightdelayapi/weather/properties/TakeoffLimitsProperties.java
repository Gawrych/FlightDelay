package com.flightDelay.flightdelayapi.weather.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "takeoff")
public class TakeoffLimitsProperties implements FlightPhaseProperties {

    private final int upperThresholdOfCrosswindKt;

    private final int lowerThresholdOfCrosswindKt;

    private final int upperThresholdOfTailwindKt;

    private final int lowerThresholdOfTailwindKt;

    private final int upperThresholdOfVisibilityMeters;

    private final int lowerThresholdOfVisibilityMeters;

    private final int upperThresholdOfCloudBaseMeters;

    private final int lowerThresholdOfCloudBaseMeters;

    private final int upperThresholdOfRainMm;

    private final int lowerThresholdOfRainMm;

    private final int upperThresholdOfCrosswindInstrumentKt;

    private final int lowerThresholdOfCrosswindInstrumentKt;
}
