package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;

@FunctionalInterface
public interface FactorAverageCalculatorInstruction<T extends DelayEntityDto> {

    Double getInstruction(T dto);
}
