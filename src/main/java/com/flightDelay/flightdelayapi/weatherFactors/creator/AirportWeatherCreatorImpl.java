package com.flightDelay.flightdelayapi.weatherFactors.creator;

import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.runway.dto.RunwayWeatherDto;
import com.flightDelay.flightdelayapi.runway.mapper.RunwayWeatherMapper;
import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.shared.UnitConverter;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.mapper.AirportWeatherMapper;
import com.flightDelay.flightdelayapi.weatherFactors.model.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AirportWeatherCreatorImpl implements AirportWeatherCreator {

    private final AirportService airportService;

    private final RunwayWeatherMapper runwayWeatherMapper;

    private final AirportWeatherMapper airportWeatherMapper;

    @Override
    public AirportWeatherDto mapSingleHour(Flight flight, Weather weather) {
        Airport airport = airportService.findByAirportIdent(flight.getAirportIdent());

        return mapAirportWeatherDto(weather, airport, flight);
    }

    @Override
    public List<AirportWeatherDto> mapPeriods(Flight flight, List<Weather> weatherInPeriods) {
        Airport airport = airportService.findByAirportIdent(flight.getAirportIdent());

        return weatherInPeriods
                .stream()
                .map(weather -> mapAirportWeatherDto(weather, airport, flight))
                .toList();
    }

    private AirportWeatherDto mapAirportWeatherDto(Weather weather, Airport airport, Flight flight) {
        int elevationM = UnitConverter.feetToMeters(airport.getElevationFt());
        List<RunwayWeatherDto> runwaysDto = runwayWeatherMapper.mapFrom(airport.getRunways());

        return airportWeatherMapper.mapFrom(weather, runwaysDto, flight, elevationM);
    }
}
