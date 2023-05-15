package com.flightDelay.flightdelayapi.weatherFactors.exception;

import com.flightDelay.flightdelayapi.shared.exception.request.RequestValidationException;

public class WeatherApiDateOutOfBoundsException extends RequestValidationException {

    public WeatherApiDateOutOfBoundsException(String currentDate, String maxDate) {
        super("error.message.weatherForecastApiDateOutOfRange", new Object[]{currentDate, maxDate});
    }
}
