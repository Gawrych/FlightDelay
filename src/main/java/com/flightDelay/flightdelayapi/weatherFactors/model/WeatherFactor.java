package com.flightDelay.flightdelayapi.weatherFactors.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.shared.enums.FactorInfluence;
import com.flightDelay.flightdelayapi.weatherFactors.enums.WeatherFactorName;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherFactor {

    @JsonProperty("id")
    private WeatherFactorName id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("value")
    private int value;

    @JsonProperty("unit_name")
    private String unitName;

    @JsonProperty("unit_symbol")
    private String unitSymbol;

    @JsonProperty("influence_on_delay")
    private FactorInfluence influenceOnDelay;
}
