package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdditionalTimeRemappingInstructionImpl implements RemappingInstruction<AdditionalTimeDto> {

    private final AdditionalTimeDtoMapper mapper;

    @Override
    public AdditionalTimeDto getInstruction(AdditionalTimeDto oldRecord, AdditionalTimeDto newRecord) {
        double mergedFlights = (oldRecord.getTotalFlight() + newRecord.getTotalFlight()) / 2.0;
        double mergedAdditionalTime = oldRecord.getTotalAdditionalTimeInMinutes() + newRecord.getTotalAdditionalTimeInMinutes();

        return mapper.mapFrom(newRecord.getDate(), mergedFlights, mergedAdditionalTime);
    }
}
