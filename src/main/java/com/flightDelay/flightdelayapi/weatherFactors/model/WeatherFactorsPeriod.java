package com.flightDelay.flightdelayapi.weatherFactors.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;

import java.util.List;

public record WeatherFactorsPeriod(

        @JsonProperty("from_time")
        String fromTime,

        @JsonProperty("to_time")
        String toTime,

        @JsonProperty("factors")
        List<WeatherFactor> factors
) {}
