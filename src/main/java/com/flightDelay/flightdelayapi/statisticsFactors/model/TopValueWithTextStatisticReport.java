package com.flightDelay.flightdelayapi.statisticsFactors.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TopValueWithTextStatisticReport extends StatisticReport {

    @JsonProperty("values")
    private List<ValueWithTextHolder> values;
}
