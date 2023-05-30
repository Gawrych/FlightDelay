package com.flightDelay.flightdelayapi.runway.mapper;

import com.flightDelay.flightdelayapi.runway.Runway;
import com.flightDelay.flightdelayapi.runway.dto.RunwayWeatherDto;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class RunwayWeatherMapperImpl implements RunwayWeatherMapper {

    @Override
    public List<RunwayWeatherDto> mapFrom(@NotNull Set<Runway> runways) {
        return runways
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private RunwayWeatherDto mapToDto(@NotNull Runway runway) {
        return RunwayWeatherDto.builder()
                .id(runway.getId())
                .lighted(runway.getLighted())
                .leHeadingDegT(runway.getLeHeadingDegT())
                .heHeadingDegT(runway.getHeHeadingDegT())
                .averageElevationFt((runway.getHeElevationFt() + runway.getLeElevationFt()) / 2)
                .build();
    }
}
