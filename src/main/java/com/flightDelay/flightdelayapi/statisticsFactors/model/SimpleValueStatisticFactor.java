package com.flightDelay.flightdelayapi.statisticsFactors.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleValueStatisticFactor extends StatisticFactor {

    @JsonProperty("value")
    private double value;
}
