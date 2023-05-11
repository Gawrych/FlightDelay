package com.flightDelay.flightdelayapi.dto;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.weather.Weather;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AirportWeatherMapper {

    @Mappings({
            @Mapping(source = "flight.airportIdent", target = "airportIdent"),
            @Mapping(source = "weather", target = "weather"),
            @Mapping(source = "ceilingFt", target = "ceilingFt"),
            @Mapping(source = "flight.phase", target = "phase"),
            @Mapping(source = "runwaysDto", target = "runwaysDTO")
    })
    AirportWeatherDto mapFrom(Weather weather, List<RunwayDto> runwaysDto, Flight flight, int ceilingFt);
}