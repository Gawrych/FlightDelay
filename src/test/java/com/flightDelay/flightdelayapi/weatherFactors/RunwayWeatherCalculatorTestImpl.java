package com.flightDelay.flightdelayapi.weatherFactors;

import com.flightDelay.flightdelayapi.weatherFactors.dtos.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.enums.IlsCategory;
import com.flightDelay.flightdelayapi.shared.UnitConverter;
import com.flightDelay.flightdelayapi.weatherFactors.calculators.RunwayWeatherCalculator;

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
    public int calculateCloudBase(AirportWeatherDto airportWeatherDto, int elevationMeters) {
        return UnitConverter.feetToMeters(cloudBase);
    }
}