package com.flightDelay.flightdelayapi.weather.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "landing")
public class LandingLimitsProperties implements FlightPhaseProperties {

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

    private final int upperThresholdOfCrosswindIls1Kts;

    private final int lowerThresholdOfCrosswindIls1Kts;

    private final int upperThresholdOfCrosswindIls2And3AKts;

    private final int lowerThresholdOfCrosswindIls2And3AKts;
}
