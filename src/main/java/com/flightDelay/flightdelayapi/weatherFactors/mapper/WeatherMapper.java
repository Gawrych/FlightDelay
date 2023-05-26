package com.flightDelay.flightdelayapi.weatherFactors.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.flightDelay.flightdelayapi.weatherFactors.model.Weather;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

    Weather mapSingleHour(JsonNode rootNode, int hour);

    List<Weather> mapPeriods(JsonNode rootNode, int startHour, int amountOfDays);
}
