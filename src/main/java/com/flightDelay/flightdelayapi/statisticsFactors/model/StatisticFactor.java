package com.flightDelay.flightdelayapi.statisticsFactors.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.StatisticFactorStatus;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.StatisticFactorType;
import com.flightDelay.flightdelayapi.shared.enums.FlightPhase;
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
    private EntityStatisticFactor id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("unit_symbol")
    private String unitSymbol;

    @JsonProperty("flight_phase")
    private FlightPhase phase;

    @JsonProperty("factor_type")
    private StatisticFactorType factorType;

    @JsonProperty("status")
    private StatisticFactorStatus status;
}
