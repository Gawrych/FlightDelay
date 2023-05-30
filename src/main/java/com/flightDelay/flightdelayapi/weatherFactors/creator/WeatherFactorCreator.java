package com.flightDelay.flightdelayapi.weatherFactors.creator;

import com.flightDelay.flightdelayapi.shared.enums.FactorInfluence;
import com.flightDelay.flightdelayapi.weatherFactors.enums.WeatherFactorName;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;

public interface WeatherFactorCreator {

    WeatherFactor createFactor(WeatherFactorName weatherFactorName, int value, FactorInfluence influence);
}
