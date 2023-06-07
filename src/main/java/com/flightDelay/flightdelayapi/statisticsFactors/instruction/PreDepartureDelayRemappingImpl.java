package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.BinaryOperator;

@Component
@RequiredArgsConstructor
public class PreDepartureDelayRemappingImpl implements BinaryOperator<PreDepartureDelayDto> {

    private final PreDepartureDelayDtoMapper mapper;

    @Override
    public PreDepartureDelayDto apply(PreDepartureDelayDto oldRecord, PreDepartureDelayDto newRecord) {
        double mergedDepartures = oldRecord.getNumberOfDepartures() + newRecord.getNumberOfDepartures();
        double mergedDelayTime = oldRecord.getDelayInMinutes() + newRecord.getDelayInMinutes();

        return mapper.mapFrom(newRecord.getDate(), mergedDepartures, mergedDelayTime);
    }
}