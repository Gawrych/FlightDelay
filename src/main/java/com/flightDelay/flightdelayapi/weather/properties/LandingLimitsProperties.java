package com.flightDelay.flightdelayapi.weather.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "landing")
public class LandingLimitsProperties implements FlightPhaseProperties {

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

    private final int upperThresholdOfCrosswindIls1Kt;

    private final int lowerThresholdOfCrosswindIls1Kt;

    private final int upperThresholdOfCrosswindIls2And3AKt;

    private final int lowerThresholdOfCrosswindIls2And3AKt;
}
