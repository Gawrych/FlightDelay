package com.flightDelay.flightdelayapi.traffic;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TrafficDtoMapper {

    @Mappings({
            @Mapping(source = "traffic.date", target = "date"),
            @Mapping(source = "traffic.departures", target = "departures"),
            @Mapping(source = "traffic.arrivals", target = "arrivals"),
            @Mapping(source = "traffic.total", target = "total")
    })
    TrafficDto mapFrom(Traffic traffic);

    @Mappings({
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "mergedDeparturesTraffic", target = "departures"),
            @Mapping(source = "mergedArrivalsTraffic", target = "arrivals"),
            @Mapping(source = "mergedTotalTraffic", target = "total"),
    })
    TrafficDto mapFrom(LocalDate date,
                                 int mergedDeparturesTraffic,
                                 int mergedArrivalsTraffic,
                                 int mergedTotalTraffic);

    List<TrafficDto> mapFromList(List<Traffic> traffics);
}
