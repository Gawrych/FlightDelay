package com.flightDelay.flightdelayapi.weatherFactors.qualifiers;

import com.flightDelay.flightdelayapi.shared.enums.FactorInfluence;
import com.flightDelay.flightdelayapi.weatherFactors.enums.IlsCategory;
import com.flightDelay.flightdelayapi.weatherFactors.properties.LandingLimitsProperties;
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
                    landingLimitsProperties.getLowerThresholdOfCrosswindKt(),
                    landingLimitsProperties.getUpperThresholdOfCrosswindKt());

            case CATEGORY_1 -> calculateRangeForValuesThatShouldBeSmall(crosswind,
                    landingLimitsProperties.getLowerThresholdOfCrosswindIls1Kt(),
                    landingLimitsProperties.getUpperThresholdOfCrosswindIls1Kt());

            case CATEGORY_2, CATEGORY_3A -> calculateRangeForValuesThatShouldBeSmall(crosswind,
                    landingLimitsProperties.getLowerThresholdOfCrosswindIls2And3AKt(),
                    landingLimitsProperties.getUpperThresholdOfCrosswindIls2And3AKt());

            case CATEGORY_3B, CATEGORY_3C -> FactorInfluence.HIGH; // The most airports do not support ILS category greater than 3A
        };
    }
}
