package com.flightDelay.flightdelayapi.weatherFactors.enums;

import com.flightDelay.flightdelayapi.shared.enums.FactorUnit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WeatherFactorName {

    CROSSWIND(FactorUnit.KNOTS),

    TAILWIND(FactorUnit.KNOTS),

    VISIBILITY(FactorUnit.METERS),

    CLOUDBASE(FactorUnit.METERS),

    RAIN(FactorUnit.MILLIMETERS);

    private final FactorUnit unit;
}