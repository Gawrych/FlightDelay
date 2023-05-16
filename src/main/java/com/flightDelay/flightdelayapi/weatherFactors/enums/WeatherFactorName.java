package com.flightDelay.flightdelayapi.weatherFactors.enums;

public enum WeatherFactorName {

    CROSSWIND("kt"),

    TAILWIND("kt"),

    VISIBILITY("m"),

    CLOUDBASE("m"),

    RAIN("mm");

    private final String unit;

    WeatherFactorName(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}