package com.flightDelay.flightdelayapi.weather.meteo;

import com.flightDelay.flightdelayapi.weather.Weather;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MeteoWeather extends Weather {

    private float temperature;

    private float dewPoint;
}