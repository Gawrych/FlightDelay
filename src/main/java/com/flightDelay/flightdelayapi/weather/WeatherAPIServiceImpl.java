package com.flightDelay.flightdelayapi.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.shared.DateProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

import static com.flightDelay.flightdelayapi.shared.DateProcessor.WEATHER_API_PATTERN;

@Service
@RequiredArgsConstructor
public class WeatherAPIServiceImpl implements WeatherAPIService {

    private static final String baseUrl = "https://api.open-meteo.com/v1/forecast?latitude=%.2f&" +
            "longitude=%.2f&hourly=temperature_2m,dewpoint_2m,rain,cloudcover,cloudcover_low,visibility," +
            "windspeed_10m,winddirection_10m,windgusts_10m,cloudcover_1000hPa,cloudcover_975hPa,is_day&windspeed_unit=kn&" +
            "start_date=%3$s&end_date=%3$s&timezone=auto";
    private final ObjectMapper objectMapper;
    private final AirportService airportService;

    public Weather getWeather(String airportIdent, long date) {
        String populatedURL = populateBaseUrlWithVariables(airportIdent, date);
        System.out.println(populatedURL);
        try {
            URL url = new URL(populatedURL);
            int hour = DateProcessor.getHour(date);
            JsonNode rootNode = objectMapper.readTree(url).path("hourly");
            return createWeather(rootNode, hour);
        } catch (IOException e) {
            // TODO: Create log
            throw new RuntimeException(e);
        }
    }

    private Weather createWeather(JsonNode rootNode, int hour) {
        return Weather.builder()
                .time(rootNode.path("time").get(hour).toString())
                .isDay(rootNode.path("is_day").get(hour).asBoolean())
                .temperature(rootNode.path("temperature_2m").get(hour).floatValue())
                .dewPoint(rootNode.path("dewpoint_2m").get(hour).floatValue())
                .rain(rootNode.path("rain").get(hour).floatValue())
                .cloudCover(rootNode.path("cloudcover").get(hour).asInt())
                .visibility(rootNode.path("visibility").get(hour).floatValue())
                .windDirection(rootNode.path("winddirection_10m").get(hour).asInt())
                .windSpeed(rootNode.path("windspeed_10m").get(hour).floatValue())
                .windGusts(rootNode.path("windgusts_10m").get(hour).floatValue())
                .build();
    }

    private String populateBaseUrlWithVariables(String airportIdent, long date) {
        Airport airport = airportService.findByAirportIdent(airportIdent);
        Double latitude = airport.getLatitudeDeg();
        Double longitude = airport.getLongitudeDeg();
        String dateInPattern = DateProcessor.parse(date, WEATHER_API_PATTERN);
        return String.format(baseUrl, latitude, longitude, dateInPattern);
    }
}
