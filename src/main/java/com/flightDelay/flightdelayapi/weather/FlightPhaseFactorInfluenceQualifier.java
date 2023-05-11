package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.WeatherFactor;
import com.flightDelay.flightdelayapi.shared.IlsCategory;

public interface FlightPhaseFactorInfluenceQualifier {

    FactorInfluence checkLimits(WeatherFactor name, int value, IlsCategory ilsCategory);

    FactorInfluence checkCrosswindLimits(IlsCategory ilsCategory, int factorValue);

    FactorInfluence checkTailwindLimits(int factorValue);

    FactorInfluence checkVisibilityLimits(int factorValue);

    FactorInfluence checkCloudbaseLimits(int factorValue);

    FactorInfluence checkRainLimits(int factorValue);


}
