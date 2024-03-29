package com.flightDelay.flightdelayapi.weatherFactors.mapper;

import com.flightDelay.flightdelayapi.runway.dto.RunwayWeatherDto;
import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.model.Weather;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AirportWeatherMapper {

    @Mappings({
            @Mapping(source = "weather", target = "weather"),
            @Mapping(source = "elevationM", target = "elevationM"),
            @Mapping(source = "flight.airportIdent", target = "airportIdent"),
            @Mapping(source = "flight.phase", target = "phase"),
            @Mapping(source = "runwaysDto", target = "runwaysDTO")
    })
    AirportWeatherDto mapFrom(Weather weather, List<RunwayWeatherDto> runwaysDto, Flight flight, int elevationM);
}