package com.flightDelay.flightdelayapi.weatherFactors.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IlsCategory {

    NONPRECISION(0, 2400, 300),

    CATEGORY_1(1, 1800, 200),

    CATEGORY_2(2, 1000, 100),

    CATEGORY_3A(3, 700, 0),

    CATEGORY_3B(4, 150, 0),

    CATEGORY_3C(5, 0, 0);

    private final int value;

    private final int runwayVisualRangeThresholdFt;

    private final int cloudBaseThresholdFt;
}
