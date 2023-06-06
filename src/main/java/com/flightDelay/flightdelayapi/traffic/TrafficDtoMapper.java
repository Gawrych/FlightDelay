package com.flightDelay.flightdelayapi.traffic;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrafficDtoMapper {

    @Mappings({
            @Mapping(source = "traffic.flightDate", target = "date"),
            @Mapping(source = "traffic.flightDepartures", target = "departures"),
            @Mapping(source = "traffic.flightArrival", target = "arrivals"),
            @Mapping(source = "traffic.flightTotal", target = "total")
    })
    TrafficDto mapFrom(Traffic traffic);

    List<TrafficDto> mapFromList(List<Traffic> traffics);
}
