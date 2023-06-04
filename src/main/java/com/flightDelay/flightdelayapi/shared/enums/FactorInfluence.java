package com.flightDelay.flightdelayapi.shared.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FactorInfluence {

    LOW(0),
    MEDIUM(1),
    HIGH(2);

    private final int value;
}
