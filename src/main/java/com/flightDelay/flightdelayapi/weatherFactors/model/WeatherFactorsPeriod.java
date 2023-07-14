package com.flightDelay.flightdelayapi.weatherFactors.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.weatherFactors.enums.WeatherFactorName;

import java.util.Map;

public record WeatherFactorsPeriod (

        @JsonProperty("from_time")
        String fromTime,

        @JsonProperty("to_time")
        String toTime,

        @JsonProperty("factors")
        Map<WeatherFactorName, WeatherFactor> factors
) {}
