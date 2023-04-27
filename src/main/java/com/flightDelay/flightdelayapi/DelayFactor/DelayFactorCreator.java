package com.flightDelay.flightdelayapi.DelayFactor;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.FactorName;

public interface DelayFactorCreator {

    DelayFactor createFactor(FactorName factorName, int value, String unit, FactorInfluence influence);

    String getMessage(String title);
}
