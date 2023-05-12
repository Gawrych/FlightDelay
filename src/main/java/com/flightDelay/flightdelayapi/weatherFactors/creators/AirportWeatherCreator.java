package com.flightDelay.flightdelayapi.weatherFactors.creators;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;

import java.util.List;

public interface AirportWeatherCreator  {

    AirportWeatherDto mapBySingleHour(Flight flight);

    List<AirportWeatherDto> mapAllNextDayInPeriods(Flight flight);
}
