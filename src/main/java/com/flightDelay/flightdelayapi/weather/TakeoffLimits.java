package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.IlsCategory;
import lombok.RequiredArgsConstructor;
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

    public TakeoffLimits() {
        super(UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS,
                LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS,
                UPPER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS,
                LOWER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS,
                UPPER_THRESHOLD_OF_VISIBILITY_METERS,
                LOWER_THRESHOLD_OF_VISIBILITY_METERS,
                UPPER_THRESHOLD_OF_CLOUDBASE_METERS,
                LOWER_THRESHOLD_OF_CLOUDBASE_METERS,
                UPPER_THRESHOLD_OF_RAIN_MM,
                LOWER_THRESHOLD_OF_RAIN_MM);
    }

    @Override
    public FactorInfluence checkCrosswindLimits(IlsCategory ilsCategory, int factorValue) {
        if (ilsCategory.getValue() > IlsCategory.NONPRECISION.getValue()) {
            return calculateIncreasingValuesLimit(factorValue,
                    LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_INSTRUMENT_KTS,
                    UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_INSTRUMENT_KTS);
        }

        return calculateIncreasingValuesLimit(factorValue,
                LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS,
                UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONINSTRUMENT_KTS);
    }
}
