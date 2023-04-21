package com.flightDelay.flightdelayapi.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    @JsonProperty("time")
    private String time;

    @JsonProperty("is_day")
    private boolean isDay;

    @JsonProperty("temperature_2m")
    private float temperature;

    @JsonProperty("dewpoint_2m")
    private float dewPoint;

    @JsonProperty("rain")
    private float rain;

    @JsonProperty("cloudcover")
    private int cloudCover;

    @JsonProperty("visibility")
    private float visibility;

    @JsonProperty("windspeed_10m")
    private float windSpeed;

    @JsonProperty("winddirection_10m")
    private int windDirection;

    @JsonProperty("windgusts_10m")
    private float windGusts;

}


