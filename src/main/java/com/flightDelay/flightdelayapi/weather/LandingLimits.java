package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.IlsCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("landingLimits")
@RequiredArgsConstructor
public class LandingLimits extends FlightPhaseLimitsImpl {

    private final static int UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONPRECISION_KTS = 25;

    private final static int LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONPRECISION_KTS = 20;

    private final static int UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITH_ILS_1_KTS = 15;

    private final static int LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITH_ILS_1_KTS = 10;

    private final static int UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITH_ILS_2_3A_KTS = 8;

    private final static int LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITH_ILS_2_3A_KTS = 5;

    private final static int UPPER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS = 15;

    private final static int LOWER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS = 8;

    private final static int UPPER_THRESHOLD_OF_VISIBILITY_METERS = 500;

    private final static int LOWER_THRESHOLD_OF_VISIBILITY_METERS = 300;

    private final static int UPPER_THRESHOLD_OF_CLOUDBASE_METERS = 250;

    private final static int LOWER_THRESHOLD_OF_CLOUDBASE_METERS = 100;

    private final static int UPPER_THRESHOLD_OF_RAIN_MM = 10;

    private final static int LOWER_THRESHOLD_OF_RAIN_MM = 7;

    public LandingLimits() {
        super(UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONPRECISION_KTS,
                LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONPRECISION_KTS,
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
    public FactorInfluence checkCrosswindLimits(IlsCategory ilsCategory, int crosswind) {
        return switch (ilsCategory) {
            case NONPRECISION -> calculateIncreasingValuesLimit(crosswind,
                    LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONPRECISION_KTS,
                    UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_NONPRECISION_KTS);

            case CATEGORY_1 -> calculateIncreasingValuesLimit(crosswind,
                    LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITH_ILS_1_KTS,
                    UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITH_ILS_1_KTS);

            case CATEGORY_2, CATEGORY_3A -> calculateIncreasingValuesLimit(crosswind,
                    LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITH_ILS_2_3A_KTS,
                    UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITH_ILS_2_3A_KTS);

            case CATEGORY_3B, CATEGORY_3C -> FactorInfluence.HIGH; // The most airports do not support ILS category greater than 3A
        };
    }
}
