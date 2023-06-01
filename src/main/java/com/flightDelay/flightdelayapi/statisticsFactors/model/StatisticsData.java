package com.flightDelay.flightdelayapi.statisticsFactors.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class StatisticsData {

    @JsonProperty("average_values")
    private Map<String, PrecisionFactor> averages;

    @JsonProperty("top_months")
    private Map<String, PrecisionFactor> topMonths;

    public StatisticsData() {
        this.averages = new HashMap<>();
        this.topMonths = new HashMap<>();
    }

    public void add(EntityStatisticFactor factorName, PrecisionFactor factor) {
        switch(factorName.getType()) {
            case TOP_VALUE_WITH_DATE -> topMonths.put(factorName.name().toLowerCase(), factor);
            case AVERAGE -> averages.put(factorName.name().toLowerCase(), factor);
        }
    }
}
