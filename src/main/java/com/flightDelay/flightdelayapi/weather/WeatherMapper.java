package com.flightDelay.flightdelayapi.weather;

import com.fasterxml.jackson.databind.JsonNode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class WeatherMapper {

    public Weather mapFrom(JsonNode rootNode, int hour) {
        JsonNode hourNode = rootNode.path("hourly");
        return Weather.builder()
                .time(hourNode.path("time").get(hour).toString())
                .rain(hourNode.path("rain").get(hour).floatValue())
                .isDay(hourNode.path("is_day").get(hour).asBoolean())
                .dewPoint(hourNode.path("dewpoint_2m").get(hour).floatValue())
                .visibility(hourNode.path("visibility").get(hour).floatValue())
                .cloudCover(hourNode.path("cloudcover").get(hour).asInt())
                .windSpeed(hourNode.path("windspeed_10m").get(hour).floatValue())
                .windGusts(hourNode.path("windgusts_10m").get(hour).floatValue())
                .temperature(hourNode.path("temperature_2m").get(hour).floatValue())
                .windDirection(hourNode.path("winddirection_10m").get(hour).asInt())
                .build();
    }
}
