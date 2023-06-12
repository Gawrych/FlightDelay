package com.flightDelay.flightdelayapi.additionalTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AdditionalTimeDtoMapper {

    @Mappings({
            @Mapping(source = "additionalTime.flightDate", target = "date"),
            @Mapping(source = "additionalTime.totalFlight", target = "totalFlight"),
            @Mapping(source = "additionalTime.stage", target = "stage"),
            @Mapping(source = "additionalTime.totalAdditionalTimeInMinutes", target = "totalAdditionalTimeInMinutes"),
    })
    AdditionalTimeDto mapFrom(AdditionalTime additionalTime);

    @Mappings({
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "totalFlight", target = "totalFlight"),
            @Mapping(source = "stage", target = "stage"),
            @Mapping(source = "totalAdditionalTimeInMinutes", target = "totalAdditionalTimeInMinutes"),
    })
    AdditionalTimeDto mapFrom(LocalDate date,
                              AdditionalTimeStage stage,
                              double totalFlight,
                              double totalAdditionalTimeInMinutes);

    List<AdditionalTimeDto> mapFromList(List<AdditionalTime> additionalTimes);
}
