package com.flightDelay.flightdelayapi.weather;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Weather {

    private String time;

    private boolean isDay;

    private float rain;

    private int visibilityM;

    private float windSpeedKn;

    private int windDirection;
}



