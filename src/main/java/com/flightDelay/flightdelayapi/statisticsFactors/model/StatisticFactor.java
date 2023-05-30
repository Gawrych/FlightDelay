package com.flightDelay.flightdelayapi.statisticsFactors.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.FactorStatus;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.StatisticFactorName;
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
public class StatisticFactor implements PrecisionFactor {

    @JsonProperty("id")
    private StatisticFactorName id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("status")
    private FactorStatus status;
}
