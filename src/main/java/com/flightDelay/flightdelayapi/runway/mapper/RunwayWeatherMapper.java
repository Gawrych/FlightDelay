package com.flightDelay.flightdelayapi.runway.mapper;

import com.flightDelay.flightdelayapi.runway.Runway;
import com.flightDelay.flightdelayapi.runway.dto.RunwayWeatherDto;

import java.util.List;
import java.util.Set;

public interface RunwayWeatherMapper {

    List<RunwayWeatherDto> mapFrom(Set<Runway> runways);
}
