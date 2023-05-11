package com.flightDelay.flightdelayapi.weather.taf;

import java.time.LocalDateTime;

public record TafReport (

    LocalDateTime timestampFrom,

    LocalDateTime timestampTo,

    int ceilingFt,

    int visibilityM,

    float rain,

    float windSpeedKn,

    int windDirection

) {}
