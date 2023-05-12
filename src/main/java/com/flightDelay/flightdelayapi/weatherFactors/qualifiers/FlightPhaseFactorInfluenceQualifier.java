package com.flightDelay.flightdelayapi.weatherFactors.qualifiers;

import com.flightDelay.flightdelayapi.shared.enums.FactorInfluence;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FactorName;
import com.flightDelay.flightdelayapi.weatherFactors.enums.IlsCategory;

public interface FlightPhaseFactorInfluenceQualifier {

    FactorInfluence checkLimits(FactorName name, int value, IlsCategory ilsCategory);

    FactorInfluence checkCrosswindLimits(IlsCategory ilsCategory, int factorValue);

    FactorInfluence checkTailwindLimits(int factorValue);

    FactorInfluence checkVisibilityLimits(int factorValue);

    FactorInfluence checkCloudbaseLimits(int factorValue);

    FactorInfluence checkRainLimits(int factorValue);


}
