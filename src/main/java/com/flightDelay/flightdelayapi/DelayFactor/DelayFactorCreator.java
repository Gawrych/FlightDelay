package com.flightDelay.flightdelayapi.DelayFactor;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.WeatherFactor;

public interface DelayFactorCreator {

    DelayFactor createFactor(WeatherFactor weatherFactor, int value, String unit, FactorInfluence influence);

    String getMessage(String title);
}
