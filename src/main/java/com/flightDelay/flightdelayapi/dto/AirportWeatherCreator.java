package com.flightDelay.flightdelayapi.dto;

import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.shared.Flight;

public interface AirportWeatherCreator  {

    AirportWeatherDto mapFrom(Flight flight);
}
