package com.flightDelay.flightdelayapi.weather.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "takeoff")
public class TakeoffLimitsProperties implements FlightPhaseProperties {

    private final int upperThresholdOfCrosswindKts;

    private final int lowerThresholdOfCrosswindKts;

    private final int upperThresholdOfTailwindKts;

    private final int lowerThresholdOfTailwindKts;

    private final int upperThresholdOfVisibilityMeters;

    private final int lowerThresholdOfVisibilityMeters;

    private final int upperThresholdOfCloudBaseMeters;

    private final int lowerThresholdOfCloudBaseMeters;

    private final int upperThresholdOfRainMm;

    private final int lowerThresholdOfRainMm;

    private final int upperThresholdOfCrosswindInstrumentKts;

    private final int lowerThresholdOfCrosswindInstrumentKts;
}
