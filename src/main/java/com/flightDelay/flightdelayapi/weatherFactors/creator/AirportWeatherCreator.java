package com.flightDelay.flightdelayapi.weatherFactors.creator;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.shared.PrecisionTimeFlight;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;

import java.util.List;

public interface AirportWeatherCreator  {

    AirportWeatherDto mapSingleHour(PrecisionTimeFlight precisionTimeFlight);

    List<AirportWeatherDto> mapPeriods(Flight flight, int amountOfDays);
}
