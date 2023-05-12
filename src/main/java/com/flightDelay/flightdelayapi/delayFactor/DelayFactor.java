package com.flightDelay.flightdelayapi.delayFactor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.shared.enums.FactorInfluence;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelayFactor {

    @JsonProperty("title")
    private String title;

    @JsonProperty("value")
    private int value;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("influence_on_delay")
    private FactorInfluence influenceOnDelay;
}
