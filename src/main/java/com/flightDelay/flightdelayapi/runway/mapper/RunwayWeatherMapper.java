package com.flightDelay.flightdelayapi.runway.mapper;

import com.flightDelay.flightdelayapi.runway.Runway;
import com.flightDelay.flightdelayapi.runway.dto.RunwayWeatherDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface RunwayWeatherMapper {

    List<RunwayWeatherDto> mapFrom(Set<Runway> runways);
}
