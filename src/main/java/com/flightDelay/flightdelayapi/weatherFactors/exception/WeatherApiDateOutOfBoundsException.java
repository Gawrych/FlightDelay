package com.flightDelay.flightdelayapi.weatherFactors.exception;

import com.flightDelay.flightdelayapi.shared.exception.RequestValidationException;

public class WeatherApiDateOutOfBoundsException extends RequestValidationException {

    public WeatherApiDateOutOfBoundsException(String currentDate, String maxDate) {
        super("api.error.message.weatherForecastApiDateOutOfRange", new Object[]{currentDate, maxDate});
    }
}
