package com.flightDelay.flightdelayapi.shared;

public class UnitConverter {

    public static int metersToFeet(int meter) {
        return (int) Math.round((3.281 * meter));
    }

    public static int feetToMeters(int feet) {
        return (int) Math.round((feet / 3.281));
    }

    public static int knotsToKmPerHour(int knots) {
        return (int) Math.round((knots * 1.852));
    }
}
