package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;

@FunctionalInterface
public interface FactorAverageInstruction<T extends DelayEntityDto> {

    Double getInstruction(T dto);
}
