package com.flightDelay.flightdelayapi.DelayFactor;

import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.hourResponse.HourFactors;
import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.dto.AirportWeatherCreator;
import com.flightDelay.flightdelayapi.tafApi.TafApiService;
import com.flightDelay.flightdelayapi.weather.Weather;
import com.flightDelay.flightdelayapi.weather.WeatherFactorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DelayFactorServiceImpl implements DelayFactorService {

    private final WeatherFactorService weatherFactorService;

    private final AirportWeatherCreator airportWeatherCreator;

    private final TafApiService tafApiService;

    @Override
    public List<DelayFactor> getFactorsByHour(Flight flight) {
        // TODO: Check if this airport is in database
        AirportWeatherDto airportWeatherDto = airportWeatherCreator.mapFrom(flight);
        return weatherFactorService.getWeatherFactors(airportWeatherDto);
    }

    public Weather getFactorsByDay(Flight flight) {
        return tafApiService.getWeather(flight.airportIdent());
//        List<AirportWeatherDto> airportWeatherDto = airportWeatherCreator.mapAllDay(flight);
//        return airportWeatherDto.stream().map(weatherFactorService::getWeatherHourFactors).collect(Collectors.toList());
    }
}
