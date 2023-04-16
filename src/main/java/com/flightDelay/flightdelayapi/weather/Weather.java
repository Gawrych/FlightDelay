package com.flightDelay.flightdelayapi.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Weather {
    private String latitude;
    private String longitude;

    @JsonProperty("hourly_units")
    private HourlyUnits hourlyUnits;
    private Hourly hourly;

    @Getter
    @Setter
    public static class Hourly {
        private String[] time;
        private float[] temperature_2m;
    }

    @Getter
    @Setter
    public static class HourlyUnits {
        private String temperature_2m;
        private String windspeed_10m;
    }
}


