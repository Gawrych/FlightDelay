package com.flightDelay.flightdelayapi.dto;

import com.flightDelay.flightdelayapi.shared.Flight;

import java.util.List;

public interface AirportWeatherCreator  {

    AirportWeatherDto mapBySingleHour(Flight flight);

    List<AirportWeatherDto> mapAllNextDayInPeriods(Flight flight);
}
