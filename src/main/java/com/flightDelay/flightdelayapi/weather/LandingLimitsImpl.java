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

    private final static int UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITHOUT_ILS_KTS = 33;

    private final static int LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITHOUT_ILS_IN_KTS = 20;

    private final static int UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITH_ILS_1_KTS = 15;

    private final static int LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITH_ILS_1_KTS = 10;

    private final static int UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITH_ILS_2_3A_KTS = 10;

    private final static int LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITH_ILS_2_3A_KTS = 5;

    private final static int UPPER_THRESHOLD_OF_ENHANCED_HEADWIND_WITH_ILS_2_3A_KTS = 25;

    private final static int LOWER_THRESHOLD_OF_ENHANCED_HEADWIND_WITH_ILS_2_3A_KTS = 18;

    public FactorInfluence checkLimits(FactorName factorName, int factorValue, IlsCategory ilsCategory) {
        return switch (factorName) {
            case CROSSWIND -> checkCrosswindLimits(ilsCategory, factorValue);
            case HEADWIND -> checkHeadwindLimits(ilsCategory, factorValue);
        };
    }

    public FactorInfluence checkCrosswindLimits(IlsCategory ilsCategory, int crosswind) {
        return switch (ilsCategory) {
            case CATEGORY_0 -> calculateLimit(crosswind,
                    LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITHOUT_ILS_IN_KTS,
                    UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITHOUT_ILS_KTS);

            case CATEGORY_1 -> calculateLimit(crosswind,
                    LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITH_ILS_1_KTS,
                    UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITH_ILS_1_KTS);

            case CATEGORY_2, CATEGORY_3A -> calculateLimit(crosswind,
                    LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITH_ILS_2_3A_KTS,
                    UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_WITH_ILS_2_3A_KTS);
        };
    }

    private FactorInfluence checkHeadwindLimits(IlsCategory ilsCategory, int headwind) {
        if (ilsCategory.getValue() >= IlsCategory.CATEGORY_2.getValue()) {
            return calculateLimit(headwind,
                    LOWER_THRESHOLD_OF_ENHANCED_HEADWIND_WITH_ILS_2_3A_KTS,
                    UPPER_THRESHOLD_OF_ENHANCED_HEADWIND_WITH_ILS_2_3A_KTS);
        }

        return FactorInfluence.LOW;
    }

    private FactorInfluence calculateLimit(int value, int lowerLimit, int upperLimit) {
        if ((lowerLimit <= value) && (value < upperLimit)) {
            return FactorInfluence.MEDIUM;

        } else if (upperLimit <= value) {
            return FactorInfluence.HIGH;

        }

        return FactorInfluence.LOW;
    }
}
