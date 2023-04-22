package com.flightDelay.flightdelayapi.DelayFactor;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("influence_on_delay")
    private int influenceOnDelay;
}
