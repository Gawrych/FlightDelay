package com.flightDelay.flightdelayapi.dto;

import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.runway.Runway;
import com.flightDelay.flightdelayapi.runway.RunwayService;
import com.flightDelay.flightdelayapi.shared.UnitConverter;
import com.flightDelay.flightdelayapi.weather.Weather;
import com.flightDelay.flightdelayapi.weather.WeatherAPIService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AirportWeatherCreatorImpl implements AirportWeatherCreator {

    private final WeatherAPIService weatherAPIService;

    private final RunwayService runwayService;

    private final AirportService airportService;

    private final ModelMapper modelMapper;

    private final AirportWeatherMapper airportWeatherMapper;

    public AirportWeatherDto mapFrom(Flight flight) {
        Airport airport = airportService.findByAirportIdent(flight.airportIdent());
        int elevationMeters = UnitConverter.feetToMeters(airport.getElevationFt());

        List<Runway> runways = runwayService.findByAirportIdent(flight.airportIdent());
        Weather weather = weatherAPIService.getWeather(flight.airportIdent(), flight.date().getTime());

        List<RunwayDto> runwaysDto = runways.stream()
                .map(runway -> modelMapper.map(runway, RunwayDto.class)).toList();

        runwaysDto.forEach(runwayDto ->
                runwayDto.setAverageElevationFt((runwayDto.getHeElevationFt() + runwayDto.getLeElevationFt()) / 2));

        return airportWeatherMapper.mapFrom(weather, runwaysDto, flight, elevationMeters);
    }
}
