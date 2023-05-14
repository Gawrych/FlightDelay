package com.flightDelay.flightdelayapi.weatherFactors.creator;

import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.runway.RunwayDto;
import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.runway.Runway;
import com.flightDelay.flightdelayapi.shared.UnitConverter;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.model.Weather;
import com.flightDelay.flightdelayapi.weatherFactors.mapper.AirportWeatherMapper;
import com.flightDelay.flightdelayapi.weatherFactors.service.WeatherAPIService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AirportWeatherCreatorImpl implements AirportWeatherCreator {

    private final WeatherAPIService weatherAPIService;

    private final AirportService airportService;

    private final ModelMapper modelMapper;

    private final AirportWeatherMapper airportWeatherMapper;

    @Override
    public AirportWeatherDto mapBySingleHour(Flight flight) {
        String airportIdent = flight.airportIdent();
        Airport airport = getAirportData(airportIdent);

        Weather weather = weatherAPIService.getWeather(airportIdent, flight.date());

        return mapAirportWeatherDto(weather, airport, flight);
    }

    @Override
    public List<AirportWeatherDto> mapAllNextDayInPeriods(Flight flight) {
        String airportIdent = flight.airportIdent();
        Airport airport = getAirportData(airportIdent);

        List<Weather> nextDayWeather = weatherAPIService.getAllNextDayWeatherInPeriods(airportIdent);

        return nextDayWeather.stream()
                .map(weather -> mapAirportWeatherDto(weather, airport, flight))
                .toList();
    }

    private Airport getAirportData(String airportIdent) {
        return airportService.findByAirportIdent(airportIdent);
    }

    private AirportWeatherDto mapAirportWeatherDto(Weather weather, Airport airport, Flight flight) {
        int elevationM = UnitConverter.feetToMeters(airport.getElevationFt());
        List<RunwayDto> runwaysDto = mapRunwaysToDto(airport.getRunways());

        return airportWeatherMapper.mapFrom(weather, runwaysDto, flight, elevationM);
    }

    private List<RunwayDto> mapRunwaysToDto(Set<Runway> runways) {
        List<RunwayDto> runwaysDto = runways.stream()
                .map(runway -> modelMapper.map(runway, RunwayDto.class))
                .toList();

        runwaysDto.forEach(runwayDto ->
                runwayDto.setAverageElevationFt((runwayDto.getHeElevationFt() + runwayDto.getLeElevationFt()) / 2));

        return runwaysDto;
    }
}
