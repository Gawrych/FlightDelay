package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.IlsCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("takeoffLimits")
public class TakeoffLimits extends FlightPhaseLimitsImpl {


    private final static int UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS = 30;

    private final static int LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS = 20;

    private final static int UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_INSTRUMENT_KTS = 22;

    private final static int LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_INSTRUMENT_KTS = 18;

    private final static int UPPER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS = 12;

    private final static int LOWER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS = 8;

    private final static int UPPER_THRESHOLD_OF_VISIBILITY_METERS = 250;

    private final static int LOWER_THRESHOLD_OF_VISIBILITY_METERS = 200;

    private final static int UPPER_THRESHOLD_OF_CLOUDBASE_METERS = 300;

    private final static int LOWER_THRESHOLD_OF_CLOUDBASE_METERS = 150;

    private final static int UPPER_THRESHOLD_OF_RAIN_MM = 10;

    private final static int LOWER_THRESHOLD_OF_RAIN_MM = 7;

    @Override
    public FactorInfluence checkCrosswindLimits(IlsCategory ilsCategory, int factorValue) {
        if (ilsCategory.getValue() > IlsCategory.NONPRECISION.getValue()) {
            return calculateRangeForValuesThatShouldBeSmall(factorValue,
                    LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_INSTRUMENT_KTS,
                    UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_INSTRUMENT_KTS);
        }

        return calculateRangeForValuesThatShouldBeSmall(factorValue,
                LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS,
                UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS);
    }

    @Override
    public void setCrosswindThresholds() {
        this.upperThresholdOfCrosswind = UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS;
        this.lowerThresholdOfCrosswind = LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS;
    }

    @Override
    public void setTailwindThresholds() {
        this.upperThresholdOfTailwind = UPPER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS;
        this.lowerThresholdOfTailwind = LOWER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS;
    }

    @Override
    public void setVisibilityThresholds() {
        this.upperThresholdOfVisibility = UPPER_THRESHOLD_OF_VISIBILITY_METERS;
        this.lowerThresholdOfVisibility = LOWER_THRESHOLD_OF_VISIBILITY_METERS;
    }

    @Override
    public void setCloudBaseThresholds() {
        this.upperThresholdOfCloudBase = UPPER_THRESHOLD_OF_CLOUDBASE_METERS;
        this.lowerThresholdOfCloudBase = LOWER_THRESHOLD_OF_CLOUDBASE_METERS;
    }

    @Override
    public void setRainThresholds() {
        this.upperThresholdOfRain = UPPER_THRESHOLD_OF_RAIN_MM;
        this.lowerThresholdOfRain = LOWER_THRESHOLD_OF_RAIN_MM;
    }
}
