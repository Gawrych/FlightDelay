package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.FactorName;
import com.flightDelay.flightdelayapi.shared.IlsCategory;

public interface LandingLimits {

    FactorInfluence checkLimits(FactorName name, int value, IlsCategory ilsCategory);
}
