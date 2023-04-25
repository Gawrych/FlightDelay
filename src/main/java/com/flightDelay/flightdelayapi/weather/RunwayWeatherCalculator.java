package com.flightDelay.flightdelayapi.weather;

public interface RunwayWeatherCalculator {

    int calculateRunwayVisualRange(float visibility, boolean isDay);

    int calculateCloudBaseAboveRunway(float temperature, float dewPoint, int elevation);
}
