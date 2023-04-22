package com.flightDelay.flightdelayapi.dto;

import com.flightDelay.flightdelayapi.weather.Weather;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface AirportWeatherMapper {

    @Mappings({
            @Mapping(source = "person.firstName", target = "firstName"),
            @Mapping(source = "person.lastName", target = "lastName"),
            @Mapping(source = "address.street + ', ' + address.city + ', ' + address.state + ' ' + address.zipCode", target = "address")
    })
    AirportWeatherDto mapToDto(Weather weather, List<RunwayDto> runwaysDto);
}
