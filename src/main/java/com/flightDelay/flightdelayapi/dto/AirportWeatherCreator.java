package com.flightDelay.flightdelayapi.dto;

import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.shared.Flight;

import java.util.List;

public interface AirportWeatherCreator  {

    AirportWeatherDto mapFrom(Flight flight);

    List<AirportWeatherDto> mapAllDay(Flight flight);
}
