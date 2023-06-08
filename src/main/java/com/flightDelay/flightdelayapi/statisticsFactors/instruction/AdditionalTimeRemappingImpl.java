package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDtoMapper;
import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeStage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.BinaryOperator;

@Component
@RequiredArgsConstructor
public class AdditionalTimeRemappingImpl implements BinaryOperator<AdditionalTimeDto> {

    private final AdditionalTimeDtoMapper mapper;

    @Override
    public AdditionalTimeDto apply(AdditionalTimeDto oldRecord, AdditionalTimeDto newRecord) {
        double mergedFlights = (oldRecord.getTotalFlight() + newRecord.getTotalFlight()) / 2.0;
        double mergedAdditionalTime =
                oldRecord.getTotalAdditionalTimeInMinutes() + newRecord.getTotalAdditionalTimeInMinutes();

        return mapper.mapFrom(newRecord.getDate(), AdditionalTimeStage.ASMA, mergedFlights, mergedAdditionalTime);
    }
}
