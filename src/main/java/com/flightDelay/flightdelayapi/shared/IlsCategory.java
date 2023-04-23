package com.flightDelay.flightdelayapi.shared;

public enum IlsCategory {

    CATEGORY_0(0),

    CATEGORY_1(1),

    CATEGORY_2(2),

    CATEGORY_3A(3);


    private final int value;

    IlsCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
