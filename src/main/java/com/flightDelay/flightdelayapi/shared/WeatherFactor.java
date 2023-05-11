package com.flightDelay.flightdelayapi.shared;

public enum WeatherFactor {
    CROSSWIND("kt"),

    TAILWIND("kt"),

    VISIBILITY("m"),

    CLOUDBASE("m"),

    RAIN("mm");

    private final String unit;

    WeatherFactor(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}