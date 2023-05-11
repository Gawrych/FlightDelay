package com.flightDelay.flightdelayapi.dto;

import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.runway.Runway;
import com.flightDelay.flightdelayapi.runway.RunwayService;
import com.flightDelay.flightdelayapi.shared.UnitConverter;
import com.flightDelay.flightdelayapi.tafApi.TafApiService;
import com.flightDelay.flightdelayapi.weather.RunwayWeatherCalculator;
import com.flightDelay.flightdelayapi.weather.MeteoAPIService;
import com.flightDelay.flightdelayapi.weather.meteo.MeteoWeather;
import com.flightDelay.flightdelayapi.weather.taf.TafWeather;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AirportWeatherCreatorImpl implements AirportWeatherCreator {

    private final MeteoAPIService meteoAPIService;

    private final TafApiService tafApiService;

    private final RunwayService runwayService;

    private final AirportService airportService;

    private final ModelMapper modelMapper;

    private final AirportWeatherMapper airportWeatherMapper;

    private final RunwayWeatherCalculator runwayWeatherCalculator;

    public AirportWeatherDto mapFrom(Flight flight) {
        Airport airport = airportService.findByAirportIdent(flight.airportIdent());
        int elevationM = UnitConverter.feetToMeters(airport.getElevationFt());

        List<Runway> runways = runwayService.findByAirportIdent(flight.airportIdent());

        List<RunwayDto> runwaysDto = runways.stream()
                .map(runway -> modelMapper.map(runway, RunwayDto.class)).toList();

        runwaysDto.forEach(runwayDto ->
                runwayDto.setAverageElevationFt((runwayDto.getHeElevationFt() + runwayDto.getLeElevationFt()) / 2));

        MeteoWeather weather = meteoAPIService.getWeather(flight.airportIdent(), flight.date().getTime());

        int ceilingFt = getCeilingFt(weather.getTemperature(), weather.getDewPoint(), elevationM);

        return airportWeatherMapper.mapFrom(weather, runwaysDto, flight, ceilingFt);
    }

    @Override
    public List<AirportWeatherDto> mapAllDay(Flight flight) {
        Airport airport = airportService.findByAirportIdent(flight.airportIdent());
        int elevationM = UnitConverter.feetToMeters(airport.getElevationFt());

        List<Runway> runways = runwayService.findByAirportIdent(flight.airportIdent());

        List<RunwayDto> runwaysDto = runways.stream()
                .map(runway -> modelMapper.map(runway, RunwayDto.class)).toList();

        runwaysDto.forEach(runwayDto ->
                runwayDto.setAverageElevationFt((runwayDto.getHeElevationFt() + runwayDto.getLeElevationFt()) / 2));

        List<TafWeather> weatherForEachHour = tafApiService.getWeather(flight.airportIdent());

        return weatherForEachHour.stream().map(tafWeather ->  airportWeatherMapper
                .mapFrom(tafWeather, runwaysDto, flight, tafWeather.getCeilingFt()))
                .collect(Collectors.toList());
    }

    private int getCeilingFt(float temperature, float dewPoint, int elevationFt) {
        int elevation = UnitConverter.feetToMeters(elevationFt);
        int ceiling = runwayWeatherCalculator.calculateCeilingAboveRunway(temperature, dewPoint, elevation);
        return UnitConverter.metersToFeet(ceiling);
    }
}
