package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.FactorName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TakeoffLimitsImpl implements TakeoffLimits {

    private final static int UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_KTS = 30;

    private final static int LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_KTS = 20;

    private final static int UPPER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS = 12;

    private final static int LOWER_THRESHOLD_OF_ENHANCED_TAILWIND_KTS = 8;

    private final static int UPPER_THRESHOLD_OF_VISIBILITY_METERS = 250;

    private final static int LOWER_THRESHOLD_OF_VISIBILITY_METERS = 200;

    private final static int UPPER_THRESHOLD_OF_CLOUDBASE_METERS = 300;

    private final static int LOWER_THRESHOLD_OF_CLOUDBASE_METERS = 150;

    private final static int UPPER_THRESHOLD_OF_RAIN_MM = 10;

    private final static int LOWER_THRESHOLD_OF_RAIN_MM = 7;

    public FactorInfluence checkLimits(FactorName factorName, int factorValue) {
        return switch (factorName) {
            case CROSSWIND -> calculateIncreasingValuesLimit(factorValue,
                    LOWER_THRESHOLD_OF_ENHANCED_CROSSWIND_KTS,
                    UPPER_THRESHOLD_OF_ENHANCED_CROSSWIND_KTS);

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

    private FactorInfluence calculateIncreasingValuesLimit(int value, int lowerLimit, int upperLimit) {
        if ((lowerLimit <= value) && (value < upperLimit)) {
            return FactorInfluence.MEDIUM;

        } else if (value >= upperLimit) {
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
