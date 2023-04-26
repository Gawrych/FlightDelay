package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.IlsCategory;
import org.springframework.stereotype.Component;

@Component("landingFactorInfluenceQualifier")
public class LandingFactorInfluenceQualifier extends FlightPhaseFactorInfluenceQualifierImpl {

    private final LandingLimitsProperties landingLimitsProperties;

    public LandingFactorInfluenceQualifier(LandingLimitsProperties landingLimitsProperties) {
        super(landingLimitsProperties);
        this.landingLimitsProperties = landingLimitsProperties;
    }

    @Override
    public FactorInfluence checkCrosswindLimits(IlsCategory ilsCategory, int crosswind) {
        return switch (ilsCategory) {
            case NONPRECISION -> calculateRangeForValuesThatShouldBeSmall(crosswind,
                    landingLimitsProperties.getLowerThresholdOfCrosswindKts(),
                    landingLimitsProperties.getUpperThresholdOfCrosswindKts());

            case CATEGORY_1 -> calculateRangeForValuesThatShouldBeSmall(crosswind,
                    landingLimitsProperties.getLowerThresholdOfCrosswindIls1Kts(),
                    landingLimitsProperties.getUpperThresholdOfCrosswindIls1Kts());

            case CATEGORY_2, CATEGORY_3A -> calculateRangeForValuesThatShouldBeSmall(crosswind,
                    landingLimitsProperties.getLowerThresholdOfCrosswindIls2And3AKts(),
                    landingLimitsProperties.getUpperThresholdOfCrosswindIls2And3AKts());

            case CATEGORY_3B, CATEGORY_3C -> FactorInfluence.HIGH; // The most airports do not support ILS category greater than 3A
        };
    }
}
