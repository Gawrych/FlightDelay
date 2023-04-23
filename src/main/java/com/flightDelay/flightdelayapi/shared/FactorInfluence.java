package com.flightDelay.flightdelayapi.shared;

public enum FactorInfluence {

    UNKNOWN(-1),
    LOW(0),
    MEDIUM(1),
    HIGH(2);

    private final int value;

    FactorInfluence(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
