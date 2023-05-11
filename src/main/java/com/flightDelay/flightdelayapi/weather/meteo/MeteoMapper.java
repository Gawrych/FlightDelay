package com.flightDelay.flightdelayapi.weather.meteo;

import com.fasterxml.jackson.databind.JsonNode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class MeteoMapper {

    public MeteoWeather mapFromMeteoJson(JsonNode rootNode, int hour) {
        JsonNode hourNode = rootNode.path("hourly");
        return MeteoWeather.builder()
                .time(hourNode.path("time").get(hour).toString())
                .rain(hourNode.path("rain").get(hour).floatValue())
                .isDay(hourNode.path("is_day").get(hour).asBoolean())
                .dewPoint(hourNode.path("dewpoint_2m").get(hour).floatValue())
                .visibilityMeters(hourNode.path("visibility").get(hour).floatValue())
                .windSpeedKnots(hourNode.path("windspeed_10m").get(hour).floatValue())
                .temperature(hourNode.path("temperature_2m").get(hour).floatValue())
                .windDirection(hourNode.path("winddirection_10m").get(hour).asInt())
                .build();
    }
}
