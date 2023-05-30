package com.flightDelay.flightdelayapi.weatherFactors.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.flightDelay.flightdelayapi.weatherFactors.model.Weather;

import java.util.List;

public interface WeatherMapper {

    Weather mapSingleHour(JsonNode rootNode, int hour);

    List<Weather> mapPeriods(JsonNode rootNode, int startHour, int amountOfDays);
}
