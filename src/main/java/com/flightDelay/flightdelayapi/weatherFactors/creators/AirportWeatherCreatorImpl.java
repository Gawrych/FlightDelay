package com.flightDelay.flightdelayapi.weatherFactors.creators;

import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.runway.RunwayDto;
import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.runway.Runway;
import com.flightDelay.flightdelayapi.runway.RunwayService;
import com.flightDelay.flightdelayapi.shared.UnitConverter;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.models.Weather;
import com.flightDelay.flightdelayapi.weatherFactors.mappers.AirportWeatherMapper;
import com.flightDelay.flightdelayapi.weatherFactors.services.WeatherAPIService;
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

    @Override
    public AirportWeatherDto mapBySingleHour(Flight flight) {
        String airportIdent = flight.airportIdent();

        checkIfAirportFromUserIsInDatabase(airportIdent);

        int elevationM = getElevationM(airportIdent);
        List<RunwayDto> runwaysDto = calculateAverageElevationFt(airportIdent);

        Weather weather = weatherAPIService.getWeather(airportIdent, flight.date());

        return airportWeatherMapper.mapFrom(weather, runwaysDto, flight, elevationM);
    }

    @Override
    public List<AirportWeatherDto> mapAllNextDayInPeriods(Flight flight) {
        String airportIdent = flight.airportIdent();

        checkIfAirportFromUserIsInDatabase(airportIdent);

        int elevationM = getElevationM(airportIdent);
        List<RunwayDto> runwaysDto = calculateAverageElevationFt(airportIdent);

        return weatherAPIService.getAllNextDayWeatherInPeriods(airportIdent).stream()
                .map(weather -> airportWeatherMapper.mapFrom(weather, runwaysDto, flight, elevationM))
                .toList();
    }

    private void checkIfAirportFromUserIsInDatabase(String airportIdent) {
        if (!airportService.existsByAirportIdent(airportIdent)) {
            // TODO: Create custom exception
            throw new RuntimeException("Airport with icao code: " + airportIdent + " not found");
        }
    }

    private int getElevationM(String airportIdent) {
        Airport airport = airportService.findByAirportIdent(airportIdent);
        return UnitConverter.feetToMeters(airport.getElevationFt());
    }

    private List<RunwayDto> calculateAverageElevationFt(String airportIdent) {
        List<Runway> runways = runwayService.findByAirportIdent(airportIdent);

        List<RunwayDto> runwaysDto = runways.stream()
                .map(runway -> modelMapper.map(runway, RunwayDto.class))
                .toList();

        runwaysDto.forEach(runwayDto ->
                runwayDto.setAverageElevationFt((runwayDto.getHeElevationFt() + runwayDto.getLeElevationFt()) / 2));

        return runwaysDto;
    }
}
