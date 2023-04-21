package com.flightDelay.flightdelayapi.weather;

public class WindServiceImpl implements WindService {

    @Override
    public int getIlsCategory() {
        return 0;
    }

    @Override
    public int getCrossWind(int runwayHeadingDegLowNumbered, int runwayHeadingDegHighNumbered, int windDirectionDeg) {
        return 0;
    }

    @Override
    public int getHeadWind() {
        return 0;
    }
}
