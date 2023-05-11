package com.flightDelay.flightdelayapi.weather;

public interface RunwayWeatherCalculator {

    int calculateRunwayVisualRange(int visibility, boolean isDay);

    int calculateCeilingAboveRunway(float temperature, float dewPoint, int elevation);
}
