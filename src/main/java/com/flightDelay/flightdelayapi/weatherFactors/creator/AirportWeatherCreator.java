package com.flightDelay.flightdelayapi.weatherFactors.creator;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.model.Weather;

import java.util.List;

public interface AirportWeatherCreator  {

    AirportWeatherDto mapSingleHour(Flight flight, Weather weather);

    List<AirportWeatherDto> mapPeriods(Flight flight, List<Weather> weatherInPeriods);
}
