package com.flightDelay.flightdelayapi.delayFactor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.delayFactor.DelayFactor;

import java.util.List;

public record DelayFactorsPeriod(

        @JsonProperty("from_time")
        String fromTime,

        @JsonProperty("to_time")
        String toTime,

        @JsonProperty("factors")
        List<DelayFactor> factors
) {}
