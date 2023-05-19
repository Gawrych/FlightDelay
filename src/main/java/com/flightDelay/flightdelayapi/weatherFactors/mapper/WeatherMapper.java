package com.flightDelay.flightdelayapi.weatherFactors.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.flightDelay.flightdelayapi.weatherFactors.model.Weather;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class WeatherMapper {

    @Value("${weather.periods.amountOfHoursInOnePeriod}")
    private int amountOfHoursInOnePeriod;

    public Weather mapSingleHour(JsonNode rootNode, int hour) {
        JsonNode hourNode = rootNode.path("hourly");
        return Weather.builder()
                .time(hourNode.path("time").get(hour).toString().replaceAll("\"", ""))
                .rainMm(hourNode.path("rain").get(hour).floatValue())
                .isDay(hourNode.path("is_day").get(hour).asBoolean())
                .dewPoint(hourNode.path("dewpoint_2m").get(hour).floatValue())
                .visibilityM(hourNode.path("visibility").get(hour).floatValue())
                .windSpeedKn(hourNode.path("windspeed_10m").get(hour).floatValue())
                .temperature(hourNode.path("temperature_2m").get(hour).floatValue())
                .windDirection(hourNode.path("winddirection_10m").get(hour).asInt())
                .build();
    }

    public List<Weather> mapPeriods(JsonNode rootNode, int startHour, int amountOfDays) {
        JsonNode hourNode = rootNode.path("hourly");
        List<Weather> nextDayWeather = new LinkedList<>();

        int amountOfPeriods = (amountOfDays * 24) / amountOfHoursInOnePeriod;
        int endHour = startHour + (amountOfPeriods * amountOfHoursInOnePeriod);

        for (int i = startHour; i < endHour; i += amountOfHoursInOnePeriod) {
            nextDayWeather.add(Weather.builder()
                    .time(hourNode.path("time").get(i).toString().replaceAll("\"", ""))
                    .rainMm(hourNode.path("rain").get(i).floatValue())
                    .isDay(hourNode.path("is_day").get(i).asBoolean())
                    .dewPoint(hourNode.path("dewpoint_2m").get(i).floatValue())
                    .visibilityM(hourNode.path("visibility").get(i).floatValue())
                    .windSpeedKn(hourNode.path("windspeed_10m").get(i).floatValue())
                    .temperature(hourNode.path("temperature_2m").get(i).floatValue())
                    .windDirection(hourNode.path("winddirection_10m").get(i).asInt())
                    .build());
        }

        return nextDayWeather;
    }
}
