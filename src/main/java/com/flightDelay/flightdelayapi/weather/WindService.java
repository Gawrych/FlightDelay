package com.flightDelay.flightdelayapi.weather;

public interface WindService {

    int getIlsCategory();
    int getCrossWind(int runwayHeadingDegLowNumbered, int runwayHeadingDegHighNumbered, int windDirectionDeg);
    int getHeadWind();
}
