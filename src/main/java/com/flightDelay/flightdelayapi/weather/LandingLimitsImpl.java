package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.FactorName;
import com.flightDelay.flightdelayapi.shared.IlsCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LandingLimitsImpl implements LandingLimits {

    private final static int CROSSWIND_LIMIT_WITHOUT_ILS_IN_KTS = 33;
    private final static int STANDARD_SAFETY_CROSSWIND_IN_KTS = 20;
    private final static int CROSSWIND_LIMIT_WITH_ILS_1_IN_KTS = 15;
    private final static int CROSSWIND_LIMIT_WITH_ILS_2_IN_KTS = 10;
    private final static int CROSSWIND_LIMIT_WITH_ILS_3A_IN_KTS = 10;
    private final static int CROSSWIND_LIMIT_WITH_ILS_3B_IN_KTS = 5;
    private final static int HEADWIND_LIMIT_IN_KTS = 25;
    private final static int STANDARD_SAFETY_HEADWIND_LIMIT_IN_KTS = 18;

    public FactorInfluence checkLimits(FactorName name, int factorValue, IlsCategory ilsCategory) {
        // TODO: Log always why unknown "Support for this factor do not exist"
        FactorInfluence influence = FactorInfluence.UNKNOWN;

        if (ilsCategory == IlsCategory.UNKNOWN) {
            return influence;
        }

        if (name == FactorName.CROSSWIND) {
            influence = checkCrosswindLimits(ilsCategory, factorValue);
        }

        if (name == FactorName.HEADWIND) {
            influence = checkHeadwindLimits(ilsCategory, factorValue);
        }

        return influence;
    }

    private FactorInfluence checkHeadwindLimits(IlsCategory ilsCategory, int headwind) {
        FactorInfluence influence = FactorInfluence.LOW;

        if (IlsCategory.CATEGORY_2.getValue() > ilsCategory.getValue()) {
            return influence;
        }

        if ((STANDARD_SAFETY_HEADWIND_LIMIT_IN_KTS <= headwind) && (headwind < HEADWIND_LIMIT_IN_KTS)) {
            influence = FactorInfluence.MEDIUM;

        } else if (HEADWIND_LIMIT_IN_KTS <= headwind) {
            influence = FactorInfluence.HIGH;
        }

        return influence;
    }

    public FactorInfluence checkCrosswindLimits(IlsCategory ilsCategory, int crosswind) {
        FactorInfluence influence = FactorInfluence.LOW;

        switch (ilsCategory) {
            case CATEGORY_0:

                if ((STANDARD_SAFETY_CROSSWIND_IN_KTS <= crosswind) && (crosswind < CROSSWIND_LIMIT_WITHOUT_ILS_IN_KTS)) {
                    influence = FactorInfluence.MEDIUM;

                } else if (CROSSWIND_LIMIT_WITHOUT_ILS_IN_KTS <= crosswind) {
                    influence = FactorInfluence.HIGH;
                }

                break;
            case CATEGORY_1:

                if ((CROSSWIND_LIMIT_WITH_ILS_2_IN_KTS <= crosswind) && (crosswind < CROSSWIND_LIMIT_WITH_ILS_1_IN_KTS)) {
                    influence = FactorInfluence.MEDIUM;

                } else if (CROSSWIND_LIMIT_WITH_ILS_1_IN_KTS <= crosswind) {
                    influence = FactorInfluence.HIGH;
                }

                break;
            default:

                if ((CROSSWIND_LIMIT_WITH_ILS_3B_IN_KTS <= crosswind) && (crosswind < CROSSWIND_LIMIT_WITH_ILS_3A_IN_KTS)) {
                    influence = FactorInfluence.MEDIUM;

                } else if (CROSSWIND_LIMIT_WITH_ILS_3A_IN_KTS <= crosswind) {
                    influence = FactorInfluence.HIGH;
                }

                break;
        }

        return influence;
    }
}
