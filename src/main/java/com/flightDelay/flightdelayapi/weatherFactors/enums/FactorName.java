package com.flightDelay.flightdelayapi.weatherFactors.enums;

public enum FactorName {
    CROSSWIND("kt"),

    TAILWIND("kt"),

    VISIBILITY("m"),

    CLOUDBASE("m"),

    RAIN("mm");

    private final String unit;

    FactorName(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}