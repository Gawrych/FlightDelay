package com.flightDelay.flightdelayapi.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.shared.DateProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

import static com.flightDelay.flightdelayapi.shared.DateProcessorImpl.WEATHER_API_PATTERN;

@Service
@RequiredArgsConstructor
public class WeatherAPIServiceImpl implements WeatherAPIService {

    @Value("${api.openmeteo.base}")
    private String baseUrl;

    private final ObjectMapper objectMapper;

    private final AirportService airportService;

    private final WeatherMapper weatherMapper;

    private final DateProcessor dateProcessor;

    public Weather getWeather(String airportIdent, long date) {
        String populatedURL = populateBaseUrlWithVariables(airportIdent, date);
        int hour = dateProcessor.getHour(date);

        try {
            URL url = new URL(populatedURL);
            JsonNode rootNode = objectMapper.readTree(url);
            return weatherMapper.mapFrom(rootNode, hour);

        } catch (IOException e) {
            // TODO: Create custom exception
            throw new RuntimeException(e);
        }
    }

    private String populateBaseUrlWithVariables(String airportIdent, long date) {
        Airport airport = airportService.findByAirportIdent(airportIdent);

        Double latitude = airport.getLatitudeDeg();
        Double longitude = airport.getLongitudeDeg();
        String dateInPattern = dateProcessor.parse(date, WEATHER_API_PATTERN);

        return String.format(baseUrl, latitude, longitude, dateInPattern);
    }
}
