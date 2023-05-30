package com.flightDelay.flightdelayapi.weatherFactors.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WeatherFactorName {

    CROSSWIND("kt"),

    TAILWIND("kt"),

    VISIBILITY("m"),

    CLOUDBASE("m"),

    RAIN("mm");

    private final String unit;
}