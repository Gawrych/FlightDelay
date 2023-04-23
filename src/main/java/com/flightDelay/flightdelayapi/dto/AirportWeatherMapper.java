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
            @Mapping(source = "weather.day", target = "day"),
            @Mapping(source = "weather.temperature", target = "temperature"),
            @Mapping(source = "weather.dewPoint", target = "dewPoint"),
            @Mapping(source = "weather.visibility", target = "visibility"),
            @Mapping(source = "weather.windSpeed", target = "windSpeed"),
            @Mapping(source = "weather.windDirection", target = "windDirection"),
            @Mapping(source = "weather.windGusts", target = "windGusts"),
            @Mapping(source = "flight.airportIdent", target = "airportIdent"),
            @Mapping(source = "flight.phase", target = "phase"),
            @Mapping(source = "runwaysDto", target = "runwaysDTO")
    })
    AirportWeatherDto mapFrom(Weather weather, List<RunwayDto> runwaysDto, Flight flight);
}