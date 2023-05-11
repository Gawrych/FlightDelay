package com.flightDelay.flightdelayapi.weather.taf;

import com.flightDelay.flightdelayapi.weather.Weather;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TafWeather extends Weather {

    private int ceilingFt;
}
