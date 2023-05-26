package com.flightDelay.flightdelayapi.runway.mapper;

import com.flightDelay.flightdelayapi.runway.Runway;
import com.flightDelay.flightdelayapi.runway.dto.RunwayWeatherDto;

import java.util.List;
import java.util.Set;

public abstract class RunwayWeatherMapperImpl implements RunwayWeatherMapper {

    @Override
    public List<RunwayWeatherDto> mapFrom(Set<Runway> runways) {
        return runways
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private RunwayWeatherDto mapToDto(Runway runway) {
        return RunwayWeatherDto.builder()
                .id(runway.getId())
                .lighted(runway.getLighted())
                .leHeadingDegT(runway.getLeHeadingDegT())
                .heHeadingDegT(runway.getHeHeadingDegT())
                .averageElevationFt((runway.getHeElevationFt() + runway.getLeElevationFt()) / 2)
                .build();
    }
}
