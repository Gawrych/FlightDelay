package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.FactorName;
import com.flightDelay.flightdelayapi.shared.IlsCategory;
import com.flightDelay.flightdelayapi.weather.properties.FlightPhaseProperties;
import org.springframework.stereotype.Component;

@Component
public abstract class FlightPhaseFactorInfluenceQualifierImpl implements FlightPhaseFactorInfluenceQualifier {

    private final FlightPhaseProperties flightPhaseProperties;

    public FlightPhaseFactorInfluenceQualifierImpl(FlightPhaseProperties flightPhaseProperties) {
        this.flightPhaseProperties = flightPhaseProperties;
    }

    @Override
    public FactorInfluence checkLimits(FactorName factorName, int factorValue, IlsCategory ilsCategory) {
        return switch (factorName) {
            case CROSSWIND -> checkCrosswindLimits(ilsCategory, factorValue);

            case TAILWIND -> checkTailwindLimits(factorValue);

            case VISIBILITY -> checkVisibilityLimits(factorValue);

            case CLOUDBASE -> checkCloudbaseLimits(factorValue);

            case RAIN -> checkRainLimits(factorValue);
        };
    }

    protected FactorInfluence calculateRangeForValuesThatShouldBeSmall(int value, int lowerLimit, int upperLimit) {
        if ((lowerLimit <= value) && (value < upperLimit)) {
            return FactorInfluence.MEDIUM;

        } else if (value >= upperLimit) {
            return FactorInfluence.HIGH;

        }

        return FactorInfluence.LOW;
    }

    protected FactorInfluence calculateRangeForValuesThatShouldBeLarge(int value, int lowerLimit, int upperLimit) {
        if ((lowerLimit < value) && (value < upperLimit)) {
            return FactorInfluence.MEDIUM;

        } else if (value <= lowerLimit) {
            return FactorInfluence.HIGH;

        }

        return FactorInfluence.LOW;
    }

    @Override
    public FactorInfluence checkCrosswindLimits(IlsCategory ilsCategory, int factorValue) {
        return calculateRangeForValuesThatShouldBeSmall(factorValue,
                flightPhaseProperties.getLowerThresholdOfCrosswindKts(),
                flightPhaseProperties.getUpperThresholdOfCrosswindKts());
    }

    @Override
    public FactorInfluence checkTailwindLimits(int factorValue) {
        return calculateRangeForValuesThatShouldBeSmall(factorValue,
                flightPhaseProperties.getLowerThresholdOfTailwindKts(),
                flightPhaseProperties.getUpperThresholdOfTailwindKts());
    }

    @Override
    public FactorInfluence checkVisibilityLimits(int factorValue) {
        return calculateRangeForValuesThatShouldBeLarge(factorValue,
                flightPhaseProperties.getLowerThresholdOfVisibilityMeters(),
                flightPhaseProperties.getUpperThresholdOfVisibilityMeters());
    }

    @Override
    public FactorInfluence checkCloudbaseLimits(int factorValue) {
        return calculateRangeForValuesThatShouldBeLarge(factorValue,
                flightPhaseProperties.getLowerThresholdOfCloudBaseMeters(),
                flightPhaseProperties.getUpperThresholdOfCloudBaseMeters());
    }

    @Override
    public FactorInfluence checkRainLimits(int factorValue) {
        return calculateRangeForValuesThatShouldBeSmall(factorValue,
                flightPhaseProperties.getLowerThresholdOfRainMm(),
                flightPhaseProperties.getUpperThresholdOfRainMm());
    }
}
