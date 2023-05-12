package com.flightDelay.flightdelayapi.delayFactor;

import com.flightDelay.flightdelayapi.shared.enums.FactorInfluence;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FactorName;

public interface DelayFactorCreator {

    DelayFactor createFactor(FactorName factorName, int value, String unit, FactorInfluence influence);

    String getMessage(String title);
}