package com.flightDelay.flightdelayapi.weatherFactors.qualifier;

import com.flightDelay.flightdelayapi.shared.enums.FactorInfluence;
import com.flightDelay.flightdelayapi.weatherFactors.enums.WeatherFactorName;
import com.flightDelay.flightdelayapi.weatherFactors.enums.IlsCategory;

public interface FlightPhaseFactorInfluenceQualifier {

    FactorInfluence checkLimits(WeatherFactorName name, int value, IlsCategory ilsCategory);

    FactorInfluence checkCrosswindLimits(IlsCategory ilsCategory, int factorValue);

    FactorInfluence checkTailwindLimits(int factorValue);

    FactorInfluence checkVisibilityLimits(int factorValue);

    FactorInfluence checkCloudbaseLimits(int factorValue);

    FactorInfluence checkRainLimits(int factorValue);


}
