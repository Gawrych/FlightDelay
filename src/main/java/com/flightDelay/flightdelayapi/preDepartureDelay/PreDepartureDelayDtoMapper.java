package com.flightDelay.flightdelayapi.preDepartureDelay;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PreDepartureDelayDtoMapper {

    @Mappings({
            @Mapping(source = "preDepartureDelay.flightDate", target = "date"),
            @Mapping(source = "preDepartureDelay.numberOfDepartures", target = "numberOfDepartures"),
            @Mapping(source = "preDepartureDelay.delayInMinutes", target = "delayInMinutes"),
    })
    PreDepartureDelayDto mapFrom(PreDepartureDelay preDepartureDelay);

    @Mappings({
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "numberOfDepartures", target = "numberOfDepartures"),
            @Mapping(source = "delayInMinutes", target = "delayInMinutes"),
    })
    PreDepartureDelayDto mapFrom(LocalDate date, double numberOfDepartures, double delayInMinutes);

    List<PreDepartureDelayDto> mapFromList(List<PreDepartureDelay> preDepartureDelays);
}
