package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.flight.Flight;

public interface AirportWeatherCreator  {

    AirportWeatherDto mapFrom(Flight flight);
}
