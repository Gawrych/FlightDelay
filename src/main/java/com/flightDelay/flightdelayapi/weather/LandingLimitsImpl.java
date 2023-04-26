package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.FactorName;
import com.flightDelay.flightdelayapi.shared.IlsCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LandingLimitsImpl implements LandingLimits {


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

    public FactorInfluence checkLimits(FactorName factorName, int factorValue, IlsCategory ilsCategory) {
        return switch (factorName) {
            case CROSSWIND -> checkCrosswindLimits(ilsCategory, factorValue);

            case TAILWIND -> calculateIncreasingValuesLimit(factorValue,
                    LOWER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS,
                    UPPER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS);

            case VISIBILITY -> calculateDecreasingValuesLimit(factorValue,
                    LOWER_THRESHOLD_OF_VISIBILITY_METERS,
                    UPPER_THRESHOLD_OF_VISIBILITY_METERS);

            case CLOUDBASE -> calculateDecreasingValuesLimit(factorValue,
                    LOWER_THRESHOLD_OF_CLOUDBASE_METERS,
                    UPPER_THRESHOLD_OF_CLOUDBASE_METERS);

            case RAIN -> calculateIncreasingValuesLimit(factorValue,
                    LOWER_THRESHOLD_OF_RAIN_MM,
                    UPPER_THRESHOLD_OF_RAIN_MM);
        };
    }

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

    private FactorInfluence calculateIncreasingValuesLimit(int value, int lowerLimit, int upperLimit) {
        if ((lowerLimit <= value) && (value < upperLimit)) {
            return FactorInfluence.MEDIUM;

        } else if (upperLimit <= value) {
            return FactorInfluence.HIGH;

        }

        return FactorInfluence.LOW;
    }

    private FactorInfluence calculateDecreasingValuesLimit(int value, int lowerLimit, int upperLimit) {
        if ((lowerLimit < value) && (value < upperLimit)) {
            return FactorInfluence.MEDIUM;

        } else if (value <= lowerLimit) {
            return FactorInfluence.HIGH;

        }

        return FactorInfluence.LOW;
    }
}
