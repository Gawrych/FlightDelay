package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.dto.AirportWeatherMapper;
import com.flightDelay.flightdelayapi.dto.RunwayDto;
import com.flightDelay.flightdelayapi.flight.Flight;
import com.flightDelay.flightdelayapi.runway.Runway;
import com.flightDelay.flightdelayapi.runway.RunwayService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AirportWeatherCreatorImpl implements AirportWeatherCreator {

    private final WeatherAPIService weatherAPIService;

    private final RunwayService runwayService;

    private final ModelMapper modelMapper;

    private final AirportWeatherMapper airportWeatherMapper;

    public AirportWeatherDto mapFrom(Flight flight) {
        List<Runway> runways = runwayService.findByAirportIdent(flight.airportIdent());
        Weather weather = weatherAPIService.getWeather(flight.airportIdent(), flight.date().getTime());

        List<RunwayDto> runwaysDto = runways.stream()
                .map(runway -> modelMapper.map(runway, RunwayDto.class)).toList();

        runwaysDto.forEach(runwayDto ->
                runwayDto.setAverageElevationFt((runwayDto.getHeElevationFt() + runwayDto.getLeElevationFt()) / 2));

        return airportWeatherMapper.mapFrom(weather, runwaysDto, flight);
    }
}
