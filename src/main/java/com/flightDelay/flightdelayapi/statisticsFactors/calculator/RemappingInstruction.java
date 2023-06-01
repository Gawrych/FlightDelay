package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;

@FunctionalInterface
public interface RemappingInstruction<T extends DelayEntityDto> {

    T getInstruction(T oldRecord, T newRecord);
}

