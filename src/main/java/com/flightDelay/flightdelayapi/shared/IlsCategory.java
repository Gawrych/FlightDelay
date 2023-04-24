package com.flightDelay.flightdelayapi.shared;

public enum IlsCategory {

    CATEGORY_0(0, 2500, 200),

    CATEGORY_1(1, 1800, 200),

    CATEGORY_2(2, 1200, 100),

    CATEGORY_3A(3, 700, 0),

    CATEGORY_3B(4, 150, 0),

    CATEGORY_3C(5, 0, 0);

    private final int value;

    private final int runwayVisualRangeThresholdFt;

    private final int cloudBaseThresholdFt;

    IlsCategory(int value, int lowerRunwayVisualRangeThresholdFt, int lowerCloudBaseThresholdFt) {
        this.value = value;
        this.runwayVisualRangeThresholdFt = lowerRunwayVisualRangeThresholdFt;
        this.cloudBaseThresholdFt = lowerCloudBaseThresholdFt;
    }

    public int getValue() {
        return value;
    }

    public int getRunwayVisualRangeThresholdFt() {
        return runwayVisualRangeThresholdFt;
    }

    public int getCloudBaseThresholdFt() {
        return cloudBaseThresholdFt;
    }
}
