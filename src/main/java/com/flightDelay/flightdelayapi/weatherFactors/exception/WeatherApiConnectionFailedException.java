package com.flightDelay.flightdelayapi.weatherFactors.exception;

import com.flightDelay.flightdelayapi.shared.exception.LackOfCrucialDataException;

public class WeatherApiConnectionFailedException extends LackOfCrucialDataException {

    public WeatherApiConnectionFailedException() {
        super("api.error.message.connectionWithWeatherApiFailed", new Object[]{});
    }
}
