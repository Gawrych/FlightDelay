package com.flightDelay.flightdelayapi.shared;

public class UnitConverter {

    public static double metersToFeet(int meter) {
        return (3.281 * meter);
    }

    public static double feetToMeters(int feet) {
        return (feet / 3.281);
    }
}
