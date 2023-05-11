package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.shared.IlsCategory;
import com.flightDelay.flightdelayapi.shared.UnitConverter;

class RunwayWeatherCalculatorTestImpl implements RunwayWeatherCalculator {

    private int rvr = 0;

    private int cloudBase = 0;

    public void setCategoryValues(IlsCategory ilsCategory) {
        this.rvr = ilsCategory.getRunwayVisualRangeThresholdFt() + 10;
        this.cloudBase = ilsCategory.getCloudBaseThresholdFt() + 10;
    }

    @Override
    public int calculateRunwayVisualRange(float visibility, boolean isDay) {
        return UnitConverter.feetToMeters(rvr);
    }

    @Override
    public int calculateCeilingAboveRunway(float temperature, float dewPoint, int elevation) {
        return UnitConverter.feetToMeters(cloudBase);
    }
}