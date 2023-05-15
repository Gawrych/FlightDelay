package com.flightDelay.flightdelayapi.weatherFactors.exception;

import com.flightDelay.flightdelayapi.shared.exception.LackOfCrucialDataException;

public class WeatherApiConnectionFailedException extends LackOfCrucialDataException {

    public WeatherApiConnectionFailedException() {
        super("error.message.connectionWithWeatherApiFailed", new Object[]{});
    }
}
