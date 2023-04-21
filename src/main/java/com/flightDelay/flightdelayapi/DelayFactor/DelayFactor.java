package com.flightDelay.flightdelayapi.DelayFactor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelayFactor {
//TODO: Change name to Factor
    public static final int INFLUENCE_LOW = 0;
    public static final int INFLUENCE_MEDIUM = 1;
    public static final int INFLUENCE_HIGH = 2;

    @JsonProperty("title")
    private String title;

    @JsonProperty("value")
    private int value;

    @JsonProperty("influence_on_delay")
    private int influenceOnDelay;
}
