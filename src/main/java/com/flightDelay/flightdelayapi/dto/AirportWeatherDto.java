package com.flightDelay.flightdelayapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportWeatherDto {

    @Column(nullable = false)
    @JsonProperty("ident")
    private String airportIdent;

    @JsonProperty("is_day")
    private boolean isDay;

    @JsonProperty("temperature_2m")
    private float temperature;

    @JsonProperty("dewpoint_2m")
    private float dewPoint;

    @JsonProperty("visibility")
    private float visibility;

    @JsonProperty("windspeed_10m")
    private float windSpeed;

    @JsonProperty("winddirection_10m")
    private int windDirection;

    @JsonProperty("windgusts_10m")
    private float windGusts;

    private List<RunwayDto> runwaysDTO;
}
