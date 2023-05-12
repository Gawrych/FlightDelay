package com.flightDelay.flightdelayapi.weather.period;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.DelayFactor.DelayFactor;

import java.util.List;

public record DelayFactorsPeriod(

        @JsonProperty("from_time")
        String fromTime,

        @JsonProperty("to_time")
        String toTime,

        @JsonProperty("factors")
        List<DelayFactor> factors
) {}
