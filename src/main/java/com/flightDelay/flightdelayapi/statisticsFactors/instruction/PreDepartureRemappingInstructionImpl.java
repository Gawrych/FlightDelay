package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreDepartureRemappingInstructionImpl implements RemappingInstruction<PreDepartureDelayDto> {

    private final PreDepartureDelayDtoMapper mapper;

    @Override
    public PreDepartureDelayDto getInstruction(PreDepartureDelayDto oldRecord, PreDepartureDelayDto newRecord) {
        double mergedDepartures = oldRecord.getNumberOfDepartures() + newRecord.getNumberOfDepartures();
        double mergedDelayTime = oldRecord.getDelayInMinutes() + newRecord.getDelayInMinutes();

        return mapper.mapFrom(newRecord.getDate(), mergedDepartures, mergedDelayTime);
    }
}
