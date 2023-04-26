package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.FactorName;
import com.flightDelay.flightdelayapi.shared.IlsCategory;

public abstract class FlightPhaseLimitsImpl implements FlightPhaseLimits {

    private final int UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS;

    private final int LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS;

    private final int UPPER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS;

    private final int LOWER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS;

    private final int UPPER_THRESHOLD_OF_VISIBILITY_METERS;

    private final int LOWER_THRESHOLD_OF_VISIBILITY_METERS;

    private final int UPPER_THRESHOLD_OF_CLOUDBASE_METERS;

    private final int LOWER_THRESHOLD_OF_CLOUDBASE_METERS;

    private final int UPPER_THRESHOLD_OF_RAIN_MM;

    private final int LOWER_THRESHOLD_OF_RAIN_MM;

    public FlightPhaseLimitsImpl(int UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS,
                                 int LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS,
                                 int UPPER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS,
                                 int LOWER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS,
                                 int UPPER_THRESHOLD_OF_VISIBILITY_METERS,
                                 int LOWER_THRESHOLD_OF_VISIBILITY_METERS,
                                 int UPPER_THRESHOLD_OF_CLOUDBASE_METERS,
                                 int LOWER_THRESHOLD_OF_CLOUDBASE_METERS,
                                 int UPPER_THRESHOLD_OF_RAIN_MM,
                                 int LOWER_THRESHOLD_OF_RAIN_MM) {

        this.UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS = UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS;
        this.LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS = LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS;
        this.UPPER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS = UPPER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS;
        this.LOWER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS = LOWER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS;
        this.UPPER_THRESHOLD_OF_VISIBILITY_METERS = UPPER_THRESHOLD_OF_VISIBILITY_METERS;
        this.LOWER_THRESHOLD_OF_VISIBILITY_METERS = LOWER_THRESHOLD_OF_VISIBILITY_METERS;
        this.UPPER_THRESHOLD_OF_CLOUDBASE_METERS = UPPER_THRESHOLD_OF_CLOUDBASE_METERS;
        this.LOWER_THRESHOLD_OF_CLOUDBASE_METERS = LOWER_THRESHOLD_OF_CLOUDBASE_METERS;
        this.UPPER_THRESHOLD_OF_RAIN_MM = UPPER_THRESHOLD_OF_RAIN_MM;
        this.LOWER_THRESHOLD_OF_RAIN_MM = LOWER_THRESHOLD_OF_RAIN_MM;
    }

    public FactorInfluence checkLimits(FactorName factorName, int factorValue, IlsCategory ilsCategory) {
        return switch (factorName) {
            case CROSSWIND -> checkCrosswindLimits(ilsCategory, factorValue);

            case TAILWIND -> checkTailwindLimits(factorValue);

            case VISIBILITY -> checkVisibilityLimits(factorValue);

            case CLOUDBASE -> checkCloudbaseLimits(factorValue);

            case RAIN -> checkRainLimits(factorValue);
        };
    }

    protected FactorInfluence calculateIncreasingValuesLimit(int value, int lowerLimit, int upperLimit) {
        if ((lowerLimit <= value) && (value < upperLimit)) {
            return FactorInfluence.MEDIUM;

        } else if (value >= upperLimit) {
            return FactorInfluence.HIGH;

        }

        return FactorInfluence.LOW;
    }

    protected FactorInfluence calculateDecreasingValuesLimit(int value, int lowerLimit, int upperLimit) {
        if ((lowerLimit < value) && (value < upperLimit)) {
            return FactorInfluence.MEDIUM;

        } else if (value <= lowerLimit) {
            return FactorInfluence.HIGH;

        }

        return FactorInfluence.LOW;
    }

    @Override
    public FactorInfluence checkTailwindLimits(int factorValue) {
        return calculateIncreasingValuesLimit(factorValue,
                LOWER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS,
                UPPER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS);
    }

    @Override
    public FactorInfluence checkVisibilityLimits(int factorValue) {
        return calculateDecreasingValuesLimit(factorValue,
                LOWER_THRESHOLD_OF_VISIBILITY_METERS,
                UPPER_THRESHOLD_OF_VISIBILITY_METERS);
    }

    @Override
    public FactorInfluence checkCloudbaseLimits(int factorValue) {
        return calculateDecreasingValuesLimit(factorValue,
                LOWER_THRESHOLD_OF_CLOUDBASE_METERS,
                UPPER_THRESHOLD_OF_CLOUDBASE_METERS);
    }

    @Override
    public FactorInfluence checkRainLimits(int factorValue) {
        return calculateIncreasingValuesLimit(factorValue,
                LOWER_THRESHOLD_OF_RAIN_MM,
                UPPER_THRESHOLD_OF_RAIN_MM);
    }
}
