package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.FactorName;
import com.flightDelay.flightdelayapi.shared.IlsCategory;
import org.springframework.stereotype.Component;

@Component
public abstract class FlightPhaseLimitsImpl implements FlightPhaseLimits {

    protected int upperThresholdOfCrosswind;

    protected int lowerThresholdOfCrosswind;

    protected int upperThresholdOfTailwind;

    protected int lowerThresholdOfTailwind;

    protected int upperThresholdOfVisibility;

    protected int lowerThresholdOfVisibility;

    protected int upperThresholdOfCloudBase;

    protected int lowerThresholdOfCloudBase;

    protected int upperThresholdOfRain;

    protected int lowerThresholdOfRain;

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
        setCrosswindThresholds();
        return calculateRangeForValuesThatShouldBeSmall(factorValue,
                lowerThresholdOfCrosswind,
                upperThresholdOfCrosswind);
    }

    @Override
    public FactorInfluence checkTailwindLimits(int factorValue) {
        setTailwindThresholds();
        return calculateRangeForValuesThatShouldBeSmall(factorValue,
                lowerThresholdOfTailwind,
                upperThresholdOfTailwind);
    }

    @Override
    public FactorInfluence checkVisibilityLimits(int factorValue) {
        setVisibilityThresholds();
        return calculateRangeForValuesThatShouldBeLarge(factorValue,
                lowerThresholdOfVisibility,
                upperThresholdOfVisibility);
    }

    @Override
    public FactorInfluence checkCloudbaseLimits(int factorValue) {
        setCloudBaseThresholds();
        return calculateRangeForValuesThatShouldBeLarge(factorValue,
                lowerThresholdOfCloudBase,
                upperThresholdOfCloudBase);
    }

    @Override
    public FactorInfluence checkRainLimits(int factorValue) {
        setRainThresholds();
        return calculateRangeForValuesThatShouldBeSmall(factorValue,
                lowerThresholdOfRain,
                upperThresholdOfRain);
    }

    @Override
    public abstract void setCrosswindThresholds();

    @Override
    public abstract void setTailwindThresholds();

    @Override
    public abstract void setVisibilityThresholds();

    @Override
    public abstract void setCloudBaseThresholds();

    @Override
    public abstract void setRainThresholds();
}
