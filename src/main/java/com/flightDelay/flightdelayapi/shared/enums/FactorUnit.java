package com.flightDelay.flightdelayapi.shared.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FactorUnit {

    KNOTS("kt"),

    METERS("m"),

    MILLIMETERS("mm"),

    MINUTES("min"),

    PERCENTAGE("%"),

    TEXT("txt");

    private final String symbol;
}
