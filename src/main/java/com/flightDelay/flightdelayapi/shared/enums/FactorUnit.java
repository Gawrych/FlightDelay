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

    TEXT("txt"),

    NUMBER("num"),

    TEXT_WITH_MINUTES("txt/min"),

    TEXT_WITH_NUMBER("txt/num");

    private final String unit;
}
